package com.qianmi.mq;

import com.qianmi.domain.Pair;
import com.qianmi.domain.Resource;
import com.qianmi.service.HttpService;
import com.qianmi.service.ResourceService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaskConsumer {
    private static Logger logger = LoggerFactory.getLogger(TaskConsumer.class);

    @Autowired
    private HttpService httpService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ShadinessProducer shadinessProducer;

    @JmsListener(destination = "task.queue", concurrency = "5-30")
    public void receiveQueue(Resource resource) {
        if (resource == null)
            return;

        logger.debug("receive message url:{}", resource.getUrl());

        Integer status = 0;
        if (!httpService.ping(resource)){
            status = 2;
            shadinessProducer.send(new Pair(resource ,0));
        }

        resourceService.updateLastDetectTimeAndStatusById(resource.getId(), DateTime.now(), status);
    }

}