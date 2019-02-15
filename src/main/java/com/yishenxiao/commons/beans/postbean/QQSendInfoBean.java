package com.yishenxiao.commons.beans.postbean;

import java.io.Serializable;

public class QQSendInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String anonymous;
	private String anonymous_flag;
	private String font;
	private String group_id;
	private String message;
	private String message_id;
	private String message_type;
	private String post_type;
	private String sub_type;
	private String time;
	private String user_id;
	private String _id;
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public String getAnonymous_flag() {
		return anonymous_flag;
	}
	public void setAnonymous_flag(String anonymous_flag) {
		this.anonymous_flag = anonymous_flag;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getPost_type() {
		return post_type;
	}
	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}
	public String getSub_type() {
		return sub_type;
	}
	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	@Override
	public String toString() {
		return "QQSendInfoBean [anonymous=" + anonymous + ", anonymous_flag=" + anonymous_flag + ", font=" + font
				+ ", group_id=" + group_id + ", message=" + message + ", message_id=" + message_id + ", message_type="
				+ message_type + ", post_type=" + post_type + ", sub_type=" + sub_type + ", time=" + time + ", user_id="
				+ user_id + ", _id=" + _id + "]";
	}
}
