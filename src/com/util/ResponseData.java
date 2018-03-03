package com.util;

import java.util.HashMap;
import java.util.Map;

import com.constant.Constant;

public class ResponseData {
	private int code;
	private String msg;
	private Map<String,Object> data;
	
	public ResponseData(){
		data = new HashMap<String,Object>();
		code = Constant.RESCODE_EXCEPTION;
		msg = "system error";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	

}
