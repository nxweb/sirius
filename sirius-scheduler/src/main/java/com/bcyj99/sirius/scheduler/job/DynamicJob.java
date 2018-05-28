package com.bcyj99.sirius.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DynamicJob extends QuartzJobBean {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			ApplicationContext ac = (ApplicationContext) (context.getScheduler().getContext().get("schedulerContextKey"));
//			BaseProductService baseProductService = (BaseProductService) ac.getBean("baseProductServiceImpl");
//			BaseProduct baseProduct = baseProductService.queryProductById(65L);
			logger.info("==========================");
		} catch (Exception e) {
			logger.error("动态定时任务1发生了异常", e);
//			JobExecutionException jobExecutionException = new JobExecutionException(e);
//			jobExecutionException.unscheduleAllTriggers();
		}
	}

}
