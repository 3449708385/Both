package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserJoinGroupBean implements Serializable {
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String userName;
   private String easemobIds;
   private Long userId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEasemobIds() {
		return easemobIds;
	}
	public void setEasemobIds(String easemobIds) {
		this.easemobIds = easemobIds;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UserJoinGroupBean [userName=" + userName + ", easemobIds=" + easemobIds + ", userId=" + userId + "]";
	}
}
