package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.List;

public class FirstGroupDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long groupCategoryId;
	private String groupCategoryName;
	private List<GroupNew> groupList;
	public Long getGroupCategoryId() {
		return groupCategoryId;
	}
	public void setGroupCategoryId(Long groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}
	public String getGroupCategoryName() {
		return groupCategoryName;
	}
	public void setGroupCategoryName(String groupCategoryName) {
		this.groupCategoryName = groupCategoryName;
	}
	public List<GroupNew> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<GroupNew> groupList) {
		this.groupList = groupList;
	}
	@Override
	public String toString() {
		return "FirstGroupDataBean [groupCategoryId=" + groupCategoryId + ", groupCategoryName=" + groupCategoryName
				+ ", groupList=" + groupList + "]";
	}
}
