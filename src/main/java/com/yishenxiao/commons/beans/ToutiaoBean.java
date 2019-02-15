package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.List;

public class ToutiaoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String url;
	private String title;
	private List<String> img_url;
	private String author;
	private List<String> tag;
	private String source;
	private String time;
	private String desc;
	private String read_number;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getImg_url() {
		return img_url;
	}
	public void setImg_url(List<String> img_url) {
		this.img_url = img_url;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getTag() {
		return tag;
	}
	public void setTag(List<String> tag) {
		this.tag = tag;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRead_number() {
		return read_number;
	}
	public void setRead_number(String read_number) {
		this.read_number = read_number;
	}
	@Override
	public String toString() {
		return "ToutiaoBean [_id=" + _id + ", url=" + url + ", title=" + title + ", img_url=" + img_url + ", author="
				+ author + ", tag=" + tag + ", source=" + source + ", time=" + time + ", desc=" + desc
				+ ", read_number=" + read_number + "]";
	}
    
}
