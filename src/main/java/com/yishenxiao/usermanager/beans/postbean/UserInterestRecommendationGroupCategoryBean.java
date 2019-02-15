package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UserInterestRecommendationGroupCategoryBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupCategoryId;
    private Integer cou;
    private Long userId;
    private String dataToken;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDataToken() {
		return dataToken;
	}
	public void setDataToken(String dataToken) {
		this.dataToken = dataToken;
	}
	public String getGroupCategoryId() {
		return groupCategoryId;
	}
	public void setGroupCategoryId(String groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}
	public Integer getCou() {
		return cou;
	}
	public void setCou(Integer cou) {
		this.cou = cou;
	}
	@Override
	public String toString() {
		return "UserInterestRecommendationGroupCategoryBean [groupCategoryId=" + groupCategoryId + ", cou=" + cou
				+ ", userId=" + userId + ", dataToken=" + dataToken + "]";
	}
}
