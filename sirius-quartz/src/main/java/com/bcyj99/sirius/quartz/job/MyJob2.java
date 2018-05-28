package com.bcyj99.sirius.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSON;
import com.bcyj99.sirius.core.bus.order.service.BusOrderService;
import com.bcyj99.sirius.core.bus.order.vo.BusOrder;

public class MyJob2 extends QuartzJobBean {
	
	private static final Logger logger = LoggerFactory.getLogger(MyJob2.class);
	
	private int timeout;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("调用定时任务-MyJob2-开始....");
			ApplicationContext applicationContext = (ApplicationContext)(context.getScheduler().getContext().get("schedulerContextKey"));
			BusOrderService busOrderService = (BusOrderService) applicationContext.getBean("busOrderServiceImpl");
			BusOrder busOrder = busOrderService.quereyOrderById(100L);
			busOrder.toString();
			String busOrderStr = JSON.toJSONString(busOrder);
			logger.info("调用定时任务-MyJob2-busOrderStr:{}",busOrderStr);
		} catch (Exception e) {
			logger.error("调用定时任务-MyJob2-失败",e);
		}
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
