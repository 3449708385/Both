package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class NoticeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String t_platform;
	private String content;
	private String link;
	private String time;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getT_platform() {
		return t_platform;
	}
	public void setT_platform(String t_platform) {
		this.t_platform = t_platform;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "NoticeBean [_id=" + _id + ", t_platform=" + t_platform + ", content=" + content + ", link=" + link
				+ ", time=" + time + "]";
	}
}
