package com.bcyj99.sirius.scheduler.service.impl;

import java.util.Date;
import java.util.UUID;

import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.scheduler.service.SchedulerService;

@Service
public class SchedulerServiceImpl implements SchedulerService {
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	
	@Autowired  
    private Scheduler scheduler;  
	
    @Autowired
    @Qualifier("dynamicJobDetail")
    private JobDetail jobDetail;
    
    @Autowired
    @Qualifier("dynamicJobDetail2")
    private JobDetail jobDetail2;
    
    @Override  
    public void cronSchedule(String name, String group, CronExpression cronExpression) {  
        if (isValidExpression(cronExpression)) {  
  
            if (name == null || name.trim().equals("")) {  
                name = UUID.randomUUID().toString();  
            }  
  
            CronTriggerImpl trigger = new CronTriggerImpl();  
            trigger.setCronExpression(cronExpression);  
  
            TriggerKey triggerKey = new TriggerKey(name, group);  
  
            trigger.setJobName(jobDetail.getKey().getName());  
            trigger.setKey(triggerKey);  
  
            try {  
                scheduler.addJob(jobDetail, true);  
                if (scheduler.checkExists(triggerKey)) {  
                    scheduler.rescheduleJob(triggerKey, trigger);  
                } else {  
                    scheduler.scheduleJob(trigger);  
                }  
            } catch (SchedulerException e) {  
                throw new IllegalArgumentException(e);  
            }  
        }  
    }  
    
    @Override  
    public void cronSchedule2(String name, String group, CronExpression cronExpression) {  
        if (isValidExpression(cronExpression)) {  
  
            if (name == null || name.trim().equals("")) {  
                name = UUID.randomUUID().toString();  
            }  
  
            CronTriggerImpl trigger = new CronTriggerImpl();  
            trigger.setCronExpression(cronExpression);  
  
            TriggerKey triggerKey = new TriggerKey(name, group);  
  
            trigger.setJobName(jobDetail2.getKey().getName());  
            trigger.setKey(triggerKey);  
  
            try {  
                scheduler.addJob(jobDetail2, true);  
                if (scheduler.checkExists(triggerKey)) {  
                    scheduler.rescheduleJob(triggerKey, trigger);  
                } else {  
                    scheduler.scheduleJob(trigger);  
                }  
            } catch (SchedulerException e) {  
                throw new IllegalArgumentException(e);  
            }  
        }  
    }  
    
    @Override  
    public void simpleSchedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group) {  
  
        if (this.isValidExpression(startTime)) {  
  
            if (name == null || name.trim().equals("")) {  
                name = UUID.randomUUID().toString();  
            }  
  
            TriggerKey triggerKey = new TriggerKey(name, group);  
  
            SimpleTriggerImpl trigger = new SimpleTriggerImpl();  
            trigger.setKey(triggerKey);  
            trigger.setJobName(jobDetail.getKey().getName());  
  
            trigger.setStartTime(startTime);  
            trigger.setEndTime(endTime);  
            trigger.setRepeatCount(repeatCount);  
            trigger.setRepeatInterval(repeatInterval);  
  
            try {  
                scheduler.addJob(jobDetail, true);  
                if (scheduler.checkExists(triggerKey)) {  
                    scheduler.rescheduleJob(triggerKey, trigger);  
                } else {  
                    scheduler.scheduleJob(trigger);  
                }  
            } catch (SchedulerException e) {  
                throw new IllegalArgumentException(e);  
            }  
        }  
    }
    
    @Override  
    public void simpleSchedule2(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group) {  
  
        if (this.isValidExpression(startTime)) {  
  
            if (name == null || name.trim().equals("")) {  
                name = UUID.randomUUID().toString();  
            }  
  
            TriggerKey triggerKey = new TriggerKey(name, group);  
  
            SimpleTriggerImpl trigger = new SimpleTriggerImpl();  
            trigger.setKey(triggerKey);  
            trigger.setJobName(jobDetail2.getKey().getName());  
  
            trigger.setStartTime(startTime);  
            trigger.setEndTime(endTime);  
            trigger.setRepeatCount(repeatCount);  
            trigger.setRepeatInterval(repeatInterval);  
  
            try {  
                scheduler.addJob(jobDetail2, true);  
                if (scheduler.checkExists(triggerKey)) {  
                    scheduler.rescheduleJob(triggerKey, trigger);  
                } else {  
                    scheduler.scheduleJob(trigger);  
                }  
            } catch (SchedulerException e) {  
                throw new IllegalArgumentException(e);  
            }  
        }  
    }
  
    @Override  
    public void pauseTrigger(String triggerName, String group) {  
        try {  
            scheduler.pauseTrigger(new TriggerKey(triggerName, group));// 停止触发器  
        } catch (SchedulerException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    @Override  
    public void resumeTrigger(String triggerName, String group) {  
        try {  
            scheduler.resumeTrigger(new TriggerKey(triggerName, group));// 重启触发器  
        } catch (SchedulerException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    @Override  
    public boolean removeTrigger(String triggerName, String group) {  
        TriggerKey triggerKey = new TriggerKey(triggerName, group);  
        try {  
            scheduler.pauseTrigger(triggerKey);// 停止触发器  
            return scheduler.unscheduleJob(triggerKey);// 移除触发器  
        } catch (SchedulerException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    private boolean isValidExpression(final CronExpression cronExpression) {  
  
        CronTriggerImpl trigger = new CronTriggerImpl();  
        trigger.setCronExpression(cronExpression);  
  
        Date date = trigger.computeFirstFireTime(null);  
  
        return date != null && date.after(new Date());  
    }  
  
    private boolean isValidExpression(final Date startTime) {  
  
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();  
        trigger.setStartTime(startTime);  
  
        Date date = trigger.computeFirstFireTime(null);  
  
        return date != null && date.after(new Date());  
    }  
}
