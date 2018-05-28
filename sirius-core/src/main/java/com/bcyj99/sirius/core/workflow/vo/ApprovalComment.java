package com.bcyj99.sirius.core.workflow.vo;

import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Comment;

public class ApprovalComment {
    private String id;//审批id
    private String processInstanceId;//流程id
    private String taskId;//任务id
    private String taskName;//任务名称
    
    private String approvalUser;//审批人
    private Date approvalTime;//审批时间
    private String approvalType;//审批类型:pass-通过 reject-驳回 refuse-拒绝 abandon-废弃 activate-激活
    private String fullMessage;//审批意见
    private String nextNode;//下个节点
    
    private Date dueDate;
    private Integer priority;
    
    List<ApprovalAttachment> attachments;//审批附件
    
	public ApprovalComment() {
		super();
	}

	public ApprovalComment(Comment comment) {
		super();
		this.id = comment.getId();
		this.processInstanceId = comment.getProcessInstanceId();
		this.taskId = comment.getTaskId();
		this.approvalUser = comment.getUserId();
		this.approvalTime = comment.getTime();
		this.approvalType = comment.getType();
		this.fullMessage = comment.getFullMessage();
	}

	public ApprovalComment(String id, String processInstanceId, String taskId, String taskName, String approvalUser,
			Date approvalTime, String type, String fullMessage, List<ApprovalAttachment> attachments) {
		super();
		this.id = id;
		this.processInstanceId = processInstanceId;
		this.taskId = taskId;
		this.taskName = taskName;
		this.approvalUser = approvalUser;
		this.approvalTime = approvalTime;
		this.approvalType = type;
		this.fullMessage = fullMessage;
		this.attachments = attachments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	public List<ApprovalAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<ApprovalAttachment> attachments) {
		this.attachments = attachments;
	}

	public String getNextNode() {
		return nextNode;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
