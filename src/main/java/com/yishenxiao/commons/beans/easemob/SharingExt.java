package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;

public class SharingExt implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String title;
   private String message;
   private String url;
   private String thumburl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumburl() {
		return thumburl;
	}
	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}
	@Override
	public String toString() {
		return "{title : " + title + ", message : " + message + ", url : " + url + ", thumburl : " + thumburl + "}";
	}
   
}
