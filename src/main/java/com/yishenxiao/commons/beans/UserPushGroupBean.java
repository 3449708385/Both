package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 *
 */
public class UserPushGroupBean implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String groupId;
    private List<String> userNameList;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<String> getUserNameList() {
		return userNameList;
	}
	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}
	@Override
	public String toString() {
		return "UserPushGroupBean [groupId=" + groupId + ", userNameList=" + userNameList + "]";
	}  
}
