package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class GroupSendDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupIds;
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	@Override
	public String toString() {
		return "GroupSendDataBean [groupIds=" + groupIds + "]";
	}
}
