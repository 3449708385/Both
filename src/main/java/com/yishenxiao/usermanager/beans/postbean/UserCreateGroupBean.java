package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserCreateGroupBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String easemobId;
	private String groupName; 
	private String groupOwer; 
	private String groupHead; 
	private String groupDesc; 
	private Long groupCategoryId;
	public String getEasemobId() {
		return easemobId;
	}
	public void setEasemobId(String easemobId) {
		this.easemobId = easemobId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupOwer() {
		return groupOwer;
	}
	public void setGroupOwer(String groupOwer) {
		this.groupOwer = groupOwer;
	}
	public String getGroupHead() {
		return groupHead;
	}
	public void setGroupHead(String groupHead) {
		this.groupHead = groupHead;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public Long getGroupCategoryId() {
		return groupCategoryId;
	}
	public void setGroupCategoryId(Long groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}
	@Override
	public String toString() {
		return "UserCreateGroupBean [easemobId=" + easemobId + ", groupName=" + groupName + ", groupOwer=" + groupOwer
				+ ", groupHead=" + groupHead + ", groupDesc=" + groupDesc + ", groupCategoryId=" + groupCategoryId
				+ "]";
	}
}
