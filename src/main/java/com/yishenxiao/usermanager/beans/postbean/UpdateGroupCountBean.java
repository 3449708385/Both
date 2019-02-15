package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateGroupCountBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobGroupId;
	public String getEasemobGroupId() {
		return easemobGroupId;
	}
	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}
	@Override
	public String toString() {
		return "UpdateGroupCountBean [easemobGroupId=" + easemobGroupId + "]";
	}
}
