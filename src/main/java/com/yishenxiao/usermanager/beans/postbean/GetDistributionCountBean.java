package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class GetDistributionCountBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long dataId;
	private Integer count;
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "GetDistributionCountBean [userId=" + userId + ", dataId=" + dataId + ", count=" + count + "]";
	}
}
