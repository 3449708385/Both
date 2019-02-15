package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateGroupHeadBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobGroupId;
	private String httpGroupHead;
	public String getEasemobGroupId() {
		return easemobGroupId;
	}
	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}
	public String getHttpGroupHead() {
		return httpGroupHead;
	}
	public void setHttpGroupHead(String httpGroupHead) {
		this.httpGroupHead = httpGroupHead;
	}
	@Override
	public String toString() {
		return "UpdateGroupHeadBean [easemobGroupId=" + easemobGroupId + ", httpGroupHead=" + httpGroupHead + "]";
	}
}
