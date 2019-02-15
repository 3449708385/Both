package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

public class Menu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long parentid;

    private String menuname;

    private String parentname;

    private String url;

    private Integer type;

    private String icon;

    private Integer ordernum;

    private String menudescribe;

    private String perms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname == null ? null : parentname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public String getMenudescribe() {
        return menudescribe;
    }

    public void setMenudescribe(String menudescribe) {
        this.menudescribe = menudescribe == null ? null : menudescribe.trim();
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }

	@Override
	public String toString() {
		return "Menu [id=" + id + ", parentid=" + parentid + ", menuname=" + menuname + ", parentname=" + parentname
				+ ", url=" + url + ", type=" + type + ", icon=" + icon + ", ordernum=" + ordernum + ", menudescribe="
				+ menudescribe + ", perms=" + perms + "]";
	}
}