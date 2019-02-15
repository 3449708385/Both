package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class DeleteGroupCategoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Long groupcategoryId;
	public Long getGroupcategoryId() {
		return groupcategoryId;
	}
	public void setGroupcategoryId(Long groupcategoryId) {
		this.groupcategoryId = groupcategoryId;
	}
	@Override
	public String toString() {
		return "DeleteGroupCategoryBean [groupcategoryId=" + groupcategoryId + "]";
	}
}
