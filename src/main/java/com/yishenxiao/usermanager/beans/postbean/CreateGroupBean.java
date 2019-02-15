package com.yishenxiao.usermanager.beans.postbean;

import java.io.Serializable;

public class CreateGroupBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupOwnner;
	private String groupName;
	private String groupDesc;
	private Integer groupCount;
	private Boolean groupPublic;
	private Boolean approval;
	private Long groupType;
	private String membersList;
	public String getGroupOwnner() {
		return groupOwnner;
	}
	public void setGroupOwnner(String groupOwnner) {
		this.groupOwnner = groupOwnner;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public String getMembersList() {
		return membersList;
	}
	public void setMembersList(String membersList) {
		this.membersList = membersList;
	}
	public Integer getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}
	public Boolean getGroupPublic() {
		return groupPublic;
	}
	public void setGroupPublic(Boolean groupPublic) {
		this.groupPublic = groupPublic;
	}
	public Boolean getApproval() {
		return approval;
	}
	public void setApproval(Boolean approval) {
		this.approval = approval;
	}
	public Long getGroupType() {
		return groupType;
	}
	public void setGroupType(Long groupType) {
		this.groupType = groupType;
	}
	@Override
	public String toString() {
		return "CreateGroupBean [groupOwnner=" + groupOwnner + ", groupName=" + groupName + ", groupDesc=" + groupDesc
				+ ", groupCount=" + groupCount + ", groupPublic=" + groupPublic + ", approval=" + approval
				+ ", groupType=" + groupType + ", membersList=" + membersList + "]";
	}
}
