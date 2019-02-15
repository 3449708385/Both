package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.Date;

public class EmailBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recipient;//接收者
	private String subject;//主题
    private String content;//邮件内容
    private Date  sendTime;
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "EmailBean [recipient=" + recipient + ", subject=" + subject + ", content=" + content + ", sendTime="
				+ sendTime + "]";
	}
    
}
