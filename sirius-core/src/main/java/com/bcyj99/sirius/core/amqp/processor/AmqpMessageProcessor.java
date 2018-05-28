package com.bcyj99.sirius.core.amqp.processor;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

public class AmqpMessageProcessor {
	
	public void processMessage(Message message) {
		MessageProperties messageProperties = message.getMessageProperties();
		System.out.println("========================messageProperties:"+messageProperties);
		byte[] bodyArr = message.getBody();
		System.out.println("========================bodyArr:"+bodyArr);
	}
	
	public void processMessage(String message) {
		System.out.println("========================message:"+message);
	}
}
