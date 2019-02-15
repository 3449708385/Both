package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class KuaixunBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String _id;
    private String content;
    private String source;
    private String link_title;
    private String issue_time;
    private String link;
    private String content_lenth;
    private String url;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLink_title() {
		return link_title;
	}
	public void setLink_title(String link_title) {
		this.link_title = link_title;
	}
	public String getIssue_time() {
		return issue_time;
	}
	public void setIssue_time(String issue_time) {
		this.issue_time = issue_time;
	}
	public String getContent_lenth() {
		return content_lenth;
	}
	public void setContent_lenth(String content_lenth) {
		this.content_lenth = content_lenth;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "KuaixunBean [_id=" + _id + ", content=" + content + ", source=" + source + ", link_title=" + link_title
				+ ", issue_time=" + issue_time + ", link=" + link + ", content_lenth=" + content_lenth + ", url=" + url
				+ "]";
	}
}
