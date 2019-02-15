package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String rolename;

    private Date createtime;

    private Date updatetime;

    private Integer state;

    private String roledescribe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRoledescribe() {
        return roledescribe;
    }

    public void setRoledescribe(String roledescribe) {
        this.roledescribe = roledescribe == null ? null : roledescribe.trim();
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", rolename=" + rolename + ", createtime=" + createtime + ", updatetime=" + updatetime
				+ ", state=" + state + ", roledescribe=" + roledescribe + "]";
	}
}