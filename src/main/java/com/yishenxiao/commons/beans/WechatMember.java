package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.Date;

public class WechatMember implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Date createtime;

    private String username;

    private String remarkpyquanpin;

    private String remarkpyinitial;

    private String keyword;

    private Long attrstatus;

    private String pyinitial;

    private Integer uin;

    private String displayname;

    private String nickname;

    private Integer memberstatus;

    private String fromgroup;

    private String puid;

    private String parentusername;

    private String nicknamemd5;

    private String fromgroupmd5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRemarkpyquanpin() {
        return remarkpyquanpin;
    }

    public void setRemarkpyquanpin(String remarkpyquanpin) {
        this.remarkpyquanpin = remarkpyquanpin == null ? null : remarkpyquanpin.trim();
    }

    public String getRemarkpyinitial() {
        return remarkpyinitial;
    }

    public void setRemarkpyinitial(String remarkpyinitial) {
        this.remarkpyinitial = remarkpyinitial == null ? null : remarkpyinitial.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Long getAttrstatus() {
        return attrstatus;
    }

    public void setAttrstatus(Long attrstatus) {
        this.attrstatus = attrstatus;
    }

    public String getPyinitial() {
        return pyinitial;
    }

    public void setPyinitial(String pyinitial) {
        this.pyinitial = pyinitial == null ? null : pyinitial.trim();
    }

    public Integer getUin() {
        return uin;
    }

    public void setUin(Integer uin) {
        this.uin = uin;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname == null ? null : displayname.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getMemberstatus() {
        return memberstatus;
    }

    public void setMemberstatus(Integer memberstatus) {
        this.memberstatus = memberstatus;
    }

    public String getFromgroup() {
        return fromgroup;
    }

    public void setFromgroup(String fromgroup) {
        this.fromgroup = fromgroup == null ? null : fromgroup.trim();
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getParentusername() {
        return parentusername;
    }

    public void setParentusername(String parentusername) {
        this.parentusername = parentusername == null ? null : parentusername.trim();
    }

    public String getNicknamemd5() {
        return nicknamemd5;
    }

    public void setNicknamemd5(String nicknamemd5) {
        this.nicknamemd5 = nicknamemd5 == null ? null : nicknamemd5.trim();
    }

    public String getFromgroupmd5() {
        return fromgroupmd5;
    }

    public void setFromgroupmd5(String fromgroupmd5) {
        this.fromgroupmd5 = fromgroupmd5 == null ? null : fromgroupmd5.trim();
    }

	@Override
	public String toString() {
		return "WechatMember [id=" + id + ", createtime=" + createtime + ", username=" + username + ", remarkpyquanpin="
				+ remarkpyquanpin + ", remarkpyinitial=" + remarkpyinitial + ", keyword=" + keyword + ", attrstatus="
				+ attrstatus + ", pyinitial=" + pyinitial + ", uin=" + uin + ", displayname=" + displayname
				+ ", nickname=" + nickname + ", memberstatus=" + memberstatus + ", fromgroup=" + fromgroup + ", puid="
				+ puid + ", parentusername=" + parentusername + ", nicknamemd5=" + nicknamemd5 + ", fromgroupmd5="
				+ fromgroupmd5 + "]";
	}
    
}