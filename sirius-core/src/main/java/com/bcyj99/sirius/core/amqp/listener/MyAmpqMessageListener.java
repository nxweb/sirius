package com.bcyj99.sirius.core.amqp.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

public class MyAmpqMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		MessageProperties messageProperties = message.getMessageProperties();
		byte[] bodyArr = message.getBody();
		System.out.println("========================bodyArr:"+bodyArr);
	}

}
