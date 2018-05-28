package com.bcyj99.sirius.core.workflow.vo;

import java.util.Date;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Attachment;

public class ReviewHistoryVo {
    private String taskId;
    private String activityId;
    private String taskName;
    private String taskAssignee;
    private String taskAssigneeName;
    private String reviewResult;
    private Date reviewDate;
    private Long reviewDuration;
    private String reviewDurationStr;
    private String reviewSuggestion;
    private boolean hasAttachment;
    private List<Attachment> reviewAttachment;
    
	public ReviewHistoryVo() {
		super();
	}
	
	public ReviewHistoryVo(HistoricActivityInstance ha) {
		super();
		this.taskId = ha.getTaskId();
		this.taskName= ha.getActivityName();
		this.taskAssignee=ha.getAssignee();
		this.reviewDate= ha.getEndTime();
		this.reviewDuration=ha.getDurationInMillis();
	}
	
	public ReviewHistoryVo(HistoricTaskInstance historicTaskInstance) {
		super();
		this.taskId =historicTaskInstance.getId();
		this.taskName= historicTaskInstance.getName();
		this.taskAssignee=historicTaskInstance.getAssignee();
		this.reviewDate= historicTaskInstance.getEndTime();
		this.reviewDuration=historicTaskInstance.getDurationInMillis();
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
	public String getTaskAssignee() {
		return taskAssignee;
	}
	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
	public String getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public Long getReviewDuration() {
		return reviewDuration;
	}
	public void setReviewDuration(Long reviewDuration) {
		this.reviewDuration = reviewDuration;
	}
	public String getReviewSuggestion() {
		return reviewSuggestion;
	}
	public void setReviewSuggestion(String reviewSuggestion) {
		this.reviewSuggestion = reviewSuggestion;
	}

	public List<Attachment> getReviewAttachment() {
		return reviewAttachment;
	}

	public void setReviewAttachment(List<Attachment> reviewAttachment) {
		this.reviewAttachment = reviewAttachment;
	}

	public String getTaskAssigneeName() {
		return taskAssigneeName;
	}

	public void setTaskAssigneeName(String taskAssigneeName) {
		this.taskAssigneeName = taskAssigneeName;
	}

	public String getReviewDurationStr() {
		return reviewDurationStr;
	}

	public void setReviewDurationStr(String reviewDurationStr) {
		this.reviewDurationStr = reviewDurationStr;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
}
