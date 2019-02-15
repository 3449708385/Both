package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

/**
 * @author admin
 *
 */
public class UserRelationRole implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private Long roleid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

	@Override
	public String toString() {
		return "UserRelationRole [id=" + id + ", userid=" + userid + ", roleid=" + roleid + "]";
	}
}