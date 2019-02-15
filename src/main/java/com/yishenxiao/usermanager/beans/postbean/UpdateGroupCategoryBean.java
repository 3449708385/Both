package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateGroupCategoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long groupcategoryId;
	private String groupcategoryname;
	private String groupdescribe;
	private Integer rdindex;
	public Long getGroupcategoryId() {
		return groupcategoryId;
	}
	public void setGroupcategoryId(Long groupcategoryId) {
		this.groupcategoryId = groupcategoryId;
	}
	public String getGroupcategoryname() {
		return groupcategoryname;
	}
	public void setGroupcategoryname(String groupcategoryname) {
		this.groupcategoryname = groupcategoryname;
	}
	public String getGroupdescribe() {
		return groupdescribe;
	}
	public void setGroupdescribe(String groupdescribe) {
		this.groupdescribe = groupdescribe;
	}
	public Integer getRdindex() {
		return rdindex;
	}
	public void setRdindex(Integer rdindex) {
		this.rdindex = rdindex;
	}
	@Override
	public String toString() {
		return "UpdateGroupCategoryBean [groupcategoryId=" + groupcategoryId + ", groupcategoryname="
				+ groupcategoryname + ", groupdescribe=" + groupdescribe + ", rdindex=" + rdindex + "]";
	}
    
}
