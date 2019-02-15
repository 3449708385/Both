package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;
import java.util.List;

public class EasemobFriendBean implements Serializable{
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String action;
   private String uri;
   private List<String> entities;
   private long timestamp;
   private long duration;
   private List<String> data;
   private String application;
   private String applicationName;
   private String organization;
   private int count;
   public String getApplication() {
	return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public List<String> getEntities() {
		return entities;
	}
	public void setEntities(List<String> entities) {
		this.entities = entities;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "EasemobFriendBean [action=" + action + ", uri=" + uri + ", entities=" + entities + ", timestamp="
				+ timestamp + ", duration=" + duration + ", data=" + data + ", application=" + application
				+ ", applicationName=" + applicationName + ", organization=" + organization + ", count=" + count + "]";
	}   
}
