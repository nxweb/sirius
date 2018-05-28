package com.bcyj99.sirius.core.amqp.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.amqp.service.SpringAmqpService;
import com.bcyj99.sirius.core.common.utils.MyPropertyPlaceholderConfigurer;

@Service
public class SpringAmqpServiceImpl implements SpringAmqpService {
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void test(){
//		amqpTemplate.convertAndSend("11111");
		
		String exchange = (String) MyPropertyPlaceholderConfigurer.getProperty("rabbitmq.exchangeName");
		String routingKey = (String) MyPropertyPlaceholderConfigurer.getProperty("rabbitmq.msg.routing");
		amqpTemplate.convertAndSend(exchange, routingKey, "3333");
		
//		String queueName = (String) MyPropertyPlaceholderConfigurer.getProperty("rabbitmq.queue.a");
//		String str = (String) amqpTemplate.receiveAndConvert(queueName);
		
//		amqpTemplate.convertAndSend("myqueue", "foo");
//		String myStr = (String) amqpTemplate.receiveAndConvert("myqueue");
//		
//		System.out.println("==================================="+myStr);
	}
}
