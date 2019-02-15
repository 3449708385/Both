package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class UploadGroupHeadData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String groupname;
    private String groupicon;
    private String easemobgroupid;
    private String model;
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupicon() {
		return groupicon;
	}
	public void setGroupicon(String groupicon) {
		this.groupicon = groupicon;
	}
	public String getEasemobgroupid() {
		return easemobgroupid;
	}
	public void setEasemobgroupid(String easemobgroupid) {
		this.easemobgroupid = easemobgroupid;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "UploadGroupHeadData [groupname=" + groupname + ", groupicon=" + groupicon + ", easemobgroupid="
				+ easemobgroupid + ", model=" + model + "]";
	}  
}
