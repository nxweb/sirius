package com.bcyj99.sirius.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class HelloJob extends QuartzJobBean {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			logger.info("定时任务查询订单成功:");
			
			logger.info("定时任务查询产品成功:");
		} catch (Exception e) {
			logger.error("helloJob异常", e);
		}
	}
}
