package com.bcyj99.sirius.core.workflow.vo;

public class TaskVo {
	
	private String processInstanceId;
	private String taskId;
	private String taskDefinitionKey;
	private String assignee;
	private String businessKey;
    
	public TaskVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TaskVo(String processInstanceId, String taskId, String taskDefinitionKey, String assignee,
			String businessKey) {
		super();
		this.processInstanceId = processInstanceId;
		this.taskId = taskId;
		this.taskDefinitionKey = taskDefinitionKey;
		this.assignee = assignee;
		this.businessKey = businessKey;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
}
