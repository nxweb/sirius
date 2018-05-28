package com.bcyj99.sirius.core.sys.vo;

import java.io.Serializable;

public class SysDataDictionaryVo implements Serializable{
	private static final long serialVersionUID = -8425029453821676415L;
	
	private String dicName;
    private String dicMeaning;
	private String attributeValue;
    private String attributeMeaning;

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeMeaning() {
		return attributeMeaning;
	}

	public void setAttributeMeaning(String attributeMeaning) {
		this.attributeMeaning = attributeMeaning;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicMeaning() {
		return dicMeaning;
	}

	public void setDicMeaning(String dicMeaning) {
		this.dicMeaning = dicMeaning;
	}
}
