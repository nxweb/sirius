package com.bcyj99.sirius.core.common.vo;

public class ResultVo<E> {
    private Integer code;
    private String message;
    private E data;
    
	public ResultVo(Integer code, String message, E data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
    
}
