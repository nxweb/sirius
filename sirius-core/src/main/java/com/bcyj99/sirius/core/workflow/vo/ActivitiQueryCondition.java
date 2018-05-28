package com.bcyj99.sirius.core.workflow.vo;

public class ActivitiQueryCondition {
    private String querykey;//查询的变量名称,例如 orderNo
    private String queryType;//查询的类型:equal-相等,like-相似
    private String queryValue;//查询的值:例如 bc-2017-00569
    
	public ActivitiQueryCondition() {
		super();
	}
	
	public ActivitiQueryCondition(String querykey, String queryType, String queryValue) {
		super();
		this.querykey = querykey;
		this.queryType = queryType;
		this.queryValue = queryValue;
	}
	
	public String getQuerykey() {
		return querykey;
	}
	public void setQuerykey(String querykey) {
		this.querykey = querykey;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
    
}
