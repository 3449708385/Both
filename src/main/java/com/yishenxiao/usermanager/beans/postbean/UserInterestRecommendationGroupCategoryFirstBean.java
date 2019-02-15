package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserInterestRecommendationGroupCategoryFirstBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer groupCount;
	private Long userId;
	public Integer getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UserInterestRecommendationGroupCategoryFirstBean [groupCount=" + groupCount + ", userId=" + userId
				+ "]";
	}
   
}
