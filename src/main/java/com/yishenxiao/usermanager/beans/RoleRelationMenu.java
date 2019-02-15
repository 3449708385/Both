package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

public class RoleRelationMenu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long roleid;

    private Long menuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

	@Override
	public String toString() {
		return "RoleRelationMenu [id=" + id + ", roleid=" + roleid + ", menuid=" + menuid + "]";
	}
}