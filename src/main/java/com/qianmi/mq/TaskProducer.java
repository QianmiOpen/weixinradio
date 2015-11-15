package com.qianmi.mq;

import com.qianmi.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class TaskProducer {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@javax.annotation.Resource
	private Queue taskQueue;

	public void send(Resource resource) {
		this.jmsMessagingTemplate.convertAndSend(this.taskQueue, resource);
	}

}