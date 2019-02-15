package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateGroupInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String groupId;
    private String groupDesc;
    private String groupName;
    private Integer groupCount;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}
	@Override
	public String toString() {
		return "UpdateGroupInfoBean [groupId=" + groupId + ", groupDesc=" + groupDesc + ", groupName=" + groupName
				+ ", groupCount=" + groupCount + "]";
	}
}
