package com.bcyj99.sirius.core.workflow.vo;

import org.activiti.engine.task.Attachment;

public class ApprovalAttachment {
	private String id;//附件id
	private String fileName;//附件名
	private String fileDescription;//附件描述
	/**
	 * 附件类型，考虑到一个任务对应多个审批意见，每个审批意见对应不同的附件，
	 * 这个类型存的是审批意见的id
	 */
	private String fileType;
	private String fileUrl;//附件相对地址
	private String fileFullUrl;//附件完整地址
	
	public ApprovalAttachment() {
		super();
	}
	
	
	public ApprovalAttachment(Attachment attachment) {
		super();
		this.id = attachment.getId();
		this.fileName = attachment.getName();
		this.fileDescription = attachment.getDescription();
		this.fileType = attachment.getType();
		this.fileUrl = attachment.getUrl();
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	public String getFileFullUrl() {
		return fileFullUrl;
	}


	public void setFileFullUrl(String fileFullUrl) {
		this.fileFullUrl = fileFullUrl;
	}
}
