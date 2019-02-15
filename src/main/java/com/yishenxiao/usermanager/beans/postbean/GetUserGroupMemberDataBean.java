package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetUserGroupMemberDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobGroupId;
	private Integer limit;
	public String getEasemobGroupId() {
		return easemobGroupId;
	}
	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "GetUserGroupMemberDataBean [easemobGroupId=" + easemobGroupId + ", limit=" + limit + "]";
	}
}
