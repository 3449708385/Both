package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class CurrencyInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String coinId;

	private String pageNumber;

	private String pageSize;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
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

	@Override
	public String toString() {
		return "CurrencyInfoBean [userId=" + userId + ", coinId=" + coinId + ", pageNumber=" + pageNumber
				+ ", pageSize=" + pageSize + "]";
	}

}
