package com.bcyj99.sirius.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringApplicationContextHolder.applicationContext=applicationContext;
	}

	public static Object getSpringBean(String beanName) {
		if(SiriusStringUtils.isBlank(beanName) || applicationContext==null){
			return null;
		}
		
		Object obj = applicationContext.getBean(beanName);
		return obj;
	}
}
