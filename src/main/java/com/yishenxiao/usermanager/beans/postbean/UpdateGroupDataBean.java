package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;
import java.util.List;

public class UpdateGroupDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobGroupIdString;
	private Long userId;
	public String getEasemobGroupIdString() {
		return easemobGroupIdString;
	}
	public void setEasemobGroupIdString(String easemobGroupIdString) {
		this.easemobGroupIdString = easemobGroupIdString;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UpdateGroupDataBean [easemobGroupIdString=" + easemobGroupIdString + ", userId=" + userId + "]";
	}   
}
