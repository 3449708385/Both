package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class DeleteEasemobGroupInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String groupId;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "DeleteEasemobGroupInfoBean [groupId=" + groupId + "]";
	}
}
