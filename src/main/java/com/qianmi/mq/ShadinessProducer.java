package com.qianmi.mq;

import com.qianmi.domain.Pair;
import com.qianmi.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Map;

/**
 * Created by aqlu on 15/8/21.
 */
@Component
public class ShadinessProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @javax.annotation.Resource
    private Queue shadinessQueue;

    @Value("${shadinessQueue.retry.period}")
    private Long period;

    public void send(Pair resourcePair) {
        if(resourcePair == null){
            return;
        }

//        jmsMessagingTemplate.getJmsTemplate().setDeliveryDelay(period * (resourcePair.getRetryCnt() + 1));
        this.jmsMessagingTemplate.convertAndSend(this.shadinessQueue, resourcePair);
    }
}
