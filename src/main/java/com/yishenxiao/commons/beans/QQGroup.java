package com.yishenxiao.commons.beans;

public class QQGroup {
    private Integer id;

    private Long groupId;

    private String groupName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

	@Override
	public String toString() {
		return "QQGroup [id=" + id + ", groupId=" + groupId + ", groupName=" + groupName + "]";
	}
}