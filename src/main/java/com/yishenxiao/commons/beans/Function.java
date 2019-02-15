package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.Date;

public class Function implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String functionName;

	private String functionDesc;

	private String functionUrl;

	private Boolean show;

	private Date createTime;

	private Date updateTime;

	private Boolean enabled;

	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName == null ? null : functionName.trim();
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc == null ? null : functionDesc.trim();
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl == null ? null : functionUrl.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Function [id=" + id + ", functionName=" + functionName + ", functionDesc=" + functionDesc
				+ ", functionUrl=" + functionUrl + ", show=" + show + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", enabled=" + enabled + ", deleted=" + deleted + "]";
	}

}
