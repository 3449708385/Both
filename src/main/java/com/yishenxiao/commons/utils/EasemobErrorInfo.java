package com.yishenxiao.commons.utils;

import java.io.Serializable;

public class EasemobErrorInfo implements Serializable {
   /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Integer error_code;
    private String error_msg;
    private String error_desc;
	public Integer getError_code() {
		return error_code;
	}
	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	
    public EasemobErrorInfo(){
		
	}
	
	public EasemobErrorInfo(Integer error_code,String error_msg,String error_desc){
		this.error_code=error_code;
		this.error_msg=error_msg;
		this.error_desc=error_desc;
	}
	@Override
	public String toString() {
		return "{\"error_code\":" + error_code + ", \"error_msg\":\"" + error_msg + "\", \"error_desc\":\"" + error_desc
				+ "\"}";
	}
	   
}
