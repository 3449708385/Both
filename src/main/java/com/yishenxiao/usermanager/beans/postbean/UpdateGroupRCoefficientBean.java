package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UpdateGroupRCoefficientBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Long groupId;
    private Integer rdindex;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Integer getRdindex() {
		return rdindex;
	}
	public void setRdindex(Integer rdindex) {
		this.rdindex = rdindex;
	}
	@Override
	public String toString() {
		return "UpdateGroupRCoefficientBean [groupId=" + groupId + ", rdindex=" + rdindex + "]";
	}
}
