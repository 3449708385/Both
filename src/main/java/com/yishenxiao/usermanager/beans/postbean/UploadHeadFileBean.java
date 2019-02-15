package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class UploadHeadFileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private MultipartFile file;
    private String userName;
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "UploadHeadFileBean [file=" + file + ", userName=" + userName + "]";
	}
}
