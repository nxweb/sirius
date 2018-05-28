package com.bcyj99.sirius.core.common.constants;

public class ActivitiConstants {
    /**
     * 流程定义key-订单
     */
	public static final String PROCESS_DEFINITION_KEY_ORDER = "sirius.order.process";
	
	/**
     * 节点定义key-订单-受理提交
     */
	public static final String TASK_DEFINITION_KEY_ORDER_SASUBMIT = "saSubmit";
	
	/**
	 * 审批类型:pass-通过 reject-驳回 refuse-拒绝 abandon-废弃 activate-激活
	 */
    public static final String APPROVAL_TYPE_PASS = "pass";
    
    public static final String APPROVAL_TYPE_REJECT = "reject";
    
    public static final String APPROVAL_TYPE_REFUSE = "refuse";
    
    public static final String APPROVAL_TYPE_ABANDON = "abandon";
    
    public static final String APPROVAL_TYPE_ACTIVATE = "activate";
    
    /**
     * 查询类型
     */
    public static final String QUERY_TYPE_EQUAL = "equal";
    
    public static final String QUERY_TYPE_LIKE = "like";
}
