package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;

public class UploadFileBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
    private Long userId;
    private Long infoId;
    private int type;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	@Override
	public String toString() {
		return "UploadFileBean [path=" + path + ", userId=" + userId + ", infoId=" + infoId + ", type=" + type + "]";
	}
}
