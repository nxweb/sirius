package com.bcyj99.sirius.core.bus.order.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bcyj99.sirius.core.workflow.vo.ApprovalAttachment;
import com.bcyj99.sirius.core.workflow.vo.ApprovalComment;
import com.bcyj99.sirius.core.workflow.vo.TaskVo;

public class BusOrder {
    private Long id;

    private Long productId;

    private String orderNo;

    private String customerName;

    private BigDecimal amount;

    private String remark;

    private String status;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;
    
    private TaskVo taskVo;
    
    private ApprovalComment approvalComment;
    
    private List<ApprovalAttachment> approvalAttachments;
    
    private String destActivityId;
    
    public ApprovalComment getApprovalComment() {
		return approvalComment;
	}

	public void setApprovalComment(ApprovalComment approvalComment) {
		this.approvalComment = approvalComment;
	}

	public List<ApprovalAttachment> getApprovalAttachments() {
		return approvalAttachments;
	}

	public void setApprovalAttachments(List<ApprovalAttachment> approvalAttachments) {
		this.approvalAttachments = approvalAttachments;
	}

	public String getDestActivityId() {
		return destActivityId;
	}

	public void setDestActivityId(String destActivityId) {
		this.destActivityId = destActivityId;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

	public TaskVo getTaskVo() {
		return taskVo;
	}

	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
}