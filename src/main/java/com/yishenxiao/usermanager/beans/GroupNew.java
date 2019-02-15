package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.Date;

public class GroupNew implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String easemobgroupid;

    private String groupname;

    private String groupdesc;

    private String groupicon;

    private Date createtime;

    private String groupowner;

    private Date updatetime;

    private String username;

    private Long groupcategoryid;

    private Integer rdindex;

    private Integer groupcount;

    private String groupnamecode;

    private Integer molgroup;
    
    private String groupcategoryName;
    
    private boolean groupMemberBoolean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEasemobgroupid() {
        return easemobgroupid;
    }

    public void setEasemobgroupid(String easemobgroupid) {
        this.easemobgroupid = easemobgroupid == null ? null : easemobgroupid.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getGroupdesc() {
        return groupdesc;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc == null ? null : groupdesc.trim();
    }

    public String getGroupicon() {
        return groupicon;
    }

    public void setGroupicon(String groupicon) {
        this.groupicon = groupicon == null ? null : groupicon.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getGroupowner() {
        return groupowner;
    }

    public void setGroupowner(String groupowner) {
        this.groupowner = groupowner == null ? null : groupowner.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getGroupcategoryid() {
        return groupcategoryid;
    }

    public void setGroupcategoryid(Long groupcategoryid) {
        this.groupcategoryid = groupcategoryid;
    }

    public Integer getRdindex() {
        return rdindex;
    }

    public void setRdindex(Integer rdindex) {
        this.rdindex = rdindex;
    }

    public Integer getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(Integer groupcount) {
        this.groupcount = groupcount;
    }

    public String getGroupnamecode() {
        return groupnamecode;
    }

    public void setGroupnamecode(String groupnamecode) {
        this.groupnamecode = groupnamecode == null ? null : groupnamecode.trim();
    }

    public Integer getMolgroup() {
        return molgroup;
    }

    public void setMolgroup(Integer molgroup) {
        this.molgroup = molgroup;
    }

	public String getGroupcategoryName() {
		return groupcategoryName;
	}

	public void setGroupcategoryName(String groupcategoryName) {
		this.groupcategoryName = groupcategoryName;
	}

	public boolean isGroupMemberBoolean() {
		return groupMemberBoolean;
	}

	public void setGroupMemberBoolean(boolean groupMemberBoolean) {
		this.groupMemberBoolean = groupMemberBoolean;
	}

	@Override
	public String toString() {
		return "GroupNew [id=" + id + ", easemobgroupid=" + easemobgroupid + ", groupname=" + groupname + ", groupdesc="
				+ groupdesc + ", groupicon=" + groupicon + ", createtime=" + createtime + ", groupowner=" + groupowner
				+ ", updatetime=" + updatetime + ", username=" + username + ", groupcategoryid=" + groupcategoryid
				+ ", rdindex=" + rdindex + ", groupcount=" + groupcount + ", groupnamecode=" + groupnamecode
				+ ", molgroup=" + molgroup + ", groupcategoryName=" + groupcategoryName + ", groupMemberBoolean="
				+ groupMemberBoolean + "]";
	}
}