package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class SendRealMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String target;
	private String type;
	private String msg;
	private String from;
	private String id;
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "SendRealMessageBean [target=" + target + ", type=" + type + ", msg=" + msg + ", from=" + from + ", id="
				+ id + "]";
	}
	
}
