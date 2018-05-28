package com.bcyj99.sirius.scheduler.service;

import java.util.Date;
import org.quartz.CronExpression;

public interface SchedulerService {
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param group
	 *            Quartz CronTrigger组
	 * @param cronExpression
	 *            Quartz CronExpression
	 */
    public void cronSchedule(String name, String group, CronExpression cronExpression);
    
    public void cronSchedule2(String name, String group, CronExpression cronExpression);
    
    /** 
     * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次 
     *  
     * @param name 
     *            Quartz SimpleTrigger 名称 
     * @param startTime 
     *            调度开始时间 
     * @param endTime 
     *            调度结束时间 
     * @param repeatCount 
     *            重复执行次数 
     * @param repeatInterval 
     *            执行时间隔间 
     */ 
    public void simpleSchedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group);
    
    public void simpleSchedule2(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group);
  
    /** 
     * 暂停触发器 
     *  
     * @param triggerName 
     *            触发器名称 
     * @param group 
     *            触发器组 
     */ 
    public void pauseTrigger(String triggerName, String group);  
  
    /** 
     * 恢复触发器 
     *  
     * @param triggerName 
     *            触发器名称 
     * @param group 
     *            触发器组 
     */  
    public void resumeTrigger(String triggerName, String group);    
  
    /** 
     * 删除触发器 
     *  
     * @param triggerName 
     *            触发器名称 
     * @param group 
     *            触发器组 
     * @return 
     */  
    public boolean removeTrigger(String triggerName, String group);  
}
