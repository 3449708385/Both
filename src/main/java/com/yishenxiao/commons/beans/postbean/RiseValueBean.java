package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class RiseValueBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String pageNumber;

	private String pageSize;
	// 按涨幅的倒序还是正序排序,1：倒序，0：正序
	private String sortType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	@Override
	public String toString() {
		return "RiseValueBean [userId=" + userId + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize
				+ ", sortType=" + sortType + "]";
	}

}
