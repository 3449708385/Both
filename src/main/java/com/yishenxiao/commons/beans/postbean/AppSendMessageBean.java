package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class AppSendMessageBean implements Serializable {
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String easemobGroupId;
   private String msg;
   private String from;
	public String getEasemobGroupId() {
		return easemobGroupId;
	}
	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
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
	@Override
	public String toString() {
		return "AppSendMessageBean [easemobGroupId=" + easemobGroupId + ", msg=" + msg + ", from=" + from + "]";
	}
   
}
