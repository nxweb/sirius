package com.bcyj99.sirius.scheduler.controller;

import java.text.ParseException;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bcyj99.sirius.scheduler.service.SchedulerService;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);
	
	@Autowired
    private SchedulerService schedulerService;
    
	@RequestMapping("/cronSchedule")
    public void cronSchedule() {
    	try {
			schedulerService.cronSchedule("bcyjCronTrigger", "bcyj", new CronExpression("0 05 18 * * ?"));
			schedulerService.cronSchedule2("bcyjCronTrigger2", "bcyj", new CronExpression("0 10 18 * * ?"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
}
