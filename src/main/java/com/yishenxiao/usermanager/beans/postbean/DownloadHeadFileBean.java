package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class DownloadHeadFileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String fileName;
    private String userName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "DownloadHeadFileBean [fileName=" + fileName + ", userName=" + userName + "]";
	}
    
}
