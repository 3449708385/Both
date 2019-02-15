package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EasemobSignUpBean implements Serializable{
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String action;
   private String application;
   private String path;
   private String uri;
   private List<Map<String,Object>> entities;
   private long timestamp;
   private long duration;
   private Map<String,Object> data;
   private Map<String,Object> params;
   private String organization;
   private String applicationName;
   private int count;
   public String getAction() {
	   return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public List<Map<String, Object>> getEntities() {
		return entities;
	}
	public void setEntities(List<Map<String, Object>> entities) {
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
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	public Map<String,Object> getParams() {
		return params;
	}
	public void setParams(Map<String,Object> params) {
		this.params = params;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "EasemobSignUpBean [action=" + action + ", application=" + application + ", path=" + path + ", uri="
				+ uri + ", entities=" + entities + ", timestamp=" + timestamp + ", duration=" + duration + ", data="
				+ data + ", params=" + params + ", organization=" + organization + ", applicationName="
				+ applicationName + ", count=" + count + "]";
	}
}
