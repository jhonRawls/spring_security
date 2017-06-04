package com.security.dto;

public class ResultDto<T> {
	private String msg;
	private int code;
	private T result; // 结果数据

	private static final String SUCCESS="success";
	public ResultDto(){
		this.code=0;
		this.msg=SUCCESS;
	} 
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
