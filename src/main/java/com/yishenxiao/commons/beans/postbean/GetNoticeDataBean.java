package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;
import java.util.Date;

public class GetNoticeDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer type;
	private String creatTime;
	private Integer limit;
	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "GetNoticeDataBean [type=" + type + ", creatTime=" + creatTime + ", limit=" + limit + "]";
	}
}
