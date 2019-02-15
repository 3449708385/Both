package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class JPushSendBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String notificationTitle;
	private String msgTitle;
	private String msgContent;
	private String extrasparam;
	public String getNotificationTitle() {
		return notificationTitle;
	}
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getExtrasparam() {
		return extrasparam;
	}
	public void setExtrasparam(String extrasparam) {
		this.extrasparam = extrasparam;
	}
	@Override
	public String toString() {
		return "JPushSendBean [notificationTitle=" + notificationTitle + ", msgTitle=" + msgTitle + ", msgContent="
				+ msgContent + ", extrasparam=" + extrasparam + "]";
	}

}
