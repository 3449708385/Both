package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.List;

public class EasemobGroupBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String groupName;//群组名称，此属性为必须的
    private String groupDesc;//群组描述，此属性为必须的
    private String username;
    private boolean groupPublic;//是否是公开群，此属性为必须的
    private int maxusers;//群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
    private boolean allowinvites;//是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。
    private String owner;//群组的管理员，此属性为必须的
    private boolean approval;//加入群是否需要群主或者群管理员审批，默认是false
    private String groupIcon;//加入群是否需要群主或者群管理员审批，默认是false
    private List<String> members;//群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
    private Long groupType;
    private String groupNameCode;
    private int groupCount;
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
	public boolean getGroupPublic() {
		return groupPublic;
	}
	public void setGroupPublic(boolean groupPublic) {
		this.groupPublic = groupPublic;
	}
	public int getMaxusers() {
		return maxusers;
	}
	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}
	public boolean getAllowinvites() {
		return allowinvites;
	}
	public void setAllowinvites(boolean allowinvites) {
		this.allowinvites = allowinvites;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public boolean getApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public String getGroupIcon() {
		return groupIcon;
	}
	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public Long getGroupType() {
		return groupType;
	}
	public void setGroupType(Long groupType) {
		this.groupType = groupType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}
	public String getGroupNameCode() {
		return groupNameCode;
	}
	public void setGroupNameCode(String groupNameCode) {
		this.groupNameCode = groupNameCode;
	}
	@Override
	public String toString() {
		return "EasemobGroupBean [groupName=" + groupName + ", groupDesc=" + groupDesc + ", username=" + username
				+ ", groupPublic=" + groupPublic + ", maxusers=" + maxusers + ", allowinvites=" + allowinvites
				+ ", owner=" + owner + ", approval=" + approval + ", groupIcon=" + groupIcon + ", members=" + members
				+ ", groupType=" + groupType + ", groupNameCode=" + groupNameCode + ", groupCount=" + groupCount + "]";
	}
}
