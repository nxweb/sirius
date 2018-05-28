package com.bcyj99.sirius.quartz.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

public class MyJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("调用定时任务MyJob...."+new Date());
	}
    
	public static void main(String[] args) throws Exception {
		JobDetail jobDetail =JobBuilder.newJob(MyJob.class).withIdentity("x1","g1").build();
		
		 //设置需排除的特殊假日
        AnnualCalendar holidays = new AnnualCalendar();
        
        Calendar cal = Calendar.getInstance();
        ArrayList<Calendar> days = new ArrayList<Calendar>();
        days.add(cal);
        holidays.setDaysExcluded(days);
		
        //创建scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.addCalendar("holidays", holidays, true, true);
        
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
				.withIdentity("t1", "g1")
				.withSchedule(SimpleScheduleBuilder.repeatHourlyForever())
				.modifiedByCalendar("holidays")
				.startNow().build();
        
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
	}
}
