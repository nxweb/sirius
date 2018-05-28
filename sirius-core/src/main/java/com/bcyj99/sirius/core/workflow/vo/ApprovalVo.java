package com.bcyj99.sirius.core.workflow.vo;

import java.util.Date;
import java.util.List;

public class ApprovalVo {
	/**
	 * 审批类型:pass-通过 reject-驳回 refuse-拒绝 abandon-废弃 activate-激活
	 */
	private String approvalType;
	/**
	 * 审批意见
	 */
    private String approvalComment;
    /**
     * 审批附件
     */
    private List<ApprovalAttachment> approvalAttachments;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 限定完成的时间
     */
    private Date dueDate;
    
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getApprovalComment() {
		return approvalComment;
	}
	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}
	public List<ApprovalAttachment> getApprovalAttachments() {
		return approvalAttachments;
	}
	public void setApprovalAttachments(List<ApprovalAttachment> approvalAttachments) {
		this.approvalAttachments = approvalAttachments;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
