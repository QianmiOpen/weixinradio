package com.qianmi.mq;

import com.qianmi.domain.Event;
import com.qianmi.domain.EventStatus;
import com.qianmi.domain.Pair;
import com.qianmi.domain.Resource;
import com.qianmi.service.EventService;
import com.qianmi.service.HttpService;
import com.qianmi.service.ResourceService;
import com.qianmi.util.UUIDGen;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ShadinessConsumer {
    private static Logger logger = LoggerFactory.getLogger(ShadinessConsumer.class);

    @Autowired
    private HttpService httpService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private EventService eventService;

    @Value("${shadinessQueue.retry.count}")
    private Integer maxRetryCount;

    @Autowired
    private ShadinessProducer shadinessProducer;


    private String pingTemplate = "【ping告警】Url:%s; Method:%s;OccurredTime:%s;";

    @JmsListener(destination = "shadiness.queue", concurrency = "1-5")
    public void receiveQueue(Pair resourcePair) {
        if (resourcePair == null)
            return;

        Resource resource = resourcePair.getResource();
        Integer retryCnt = resourcePair.getRetryCnt();
        logger.debug("receive message url:{}", resource.getUrl());

        Integer status = 0;

        if (!httpService.ping(resource)) {
            if (retryCnt + 1 >= maxRetryCount) {
                status = 3;
                DateTime now = DateTime.now();
                String content = String.format(pingTemplate, resource.getUrl(), resource.getMethod(), now);

                //生成告警事件
                Event event = new Event(UUIDGen.systemUuid(), now, null, resource.getWorkNo(), content,
                        EventStatus.INITIALIZATION, resource.getId());
                eventService.save(event);
            } else {
                status = 2;
                resourcePair.setRetryCnt(retryCnt + 1);
                shadinessProducer.send(resourcePair);
            }
        }

        logger.debug("Setting shadiness resource of [{}] to {} ", resource.getUrl(), resource.getStatus());
        resourceService.updateLastDetectTimeAndStatusById(resource.getId(), DateTime.now(), status);
    }

}