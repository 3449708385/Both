package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetUserGroupMembersDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Long groupId;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "GetUserGroupMembersDataBean [groupId=" + groupId + "]";
	}   
}
