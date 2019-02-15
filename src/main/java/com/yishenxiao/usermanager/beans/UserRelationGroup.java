package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.Date;

public class UserRelationGroup implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long groupid;

    private Long userid;

    private Date createtime;

    private Integer gagstate;

    private Integer disturbstate;

    private Date updatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getGagstate() {
        return gagstate;
    }

    public void setGagstate(Integer gagstate) {
        this.gagstate = gagstate;
    }

    public Integer getDisturbstate() {
        return disturbstate;
    }

    public void setDisturbstate(Integer disturbstate) {
        this.disturbstate = disturbstate;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	@Override
	public String toString() {
		return "UserRelationGroup [id=" + id + ", groupid=" + groupid + ", userid=" + userid + ", createtime="
				+ createtime + ", gagstate=" + gagstate + ", disturbstate=" + disturbstate + ", updatetime="
				+ updatetime + "]";
	}
}