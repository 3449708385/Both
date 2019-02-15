package com.yishenxiao.usermanager.beans;

import java.io.Serializable;

public class Friends implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userid;

    private Long friendsid;

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

    public Long getFriendsid() {
        return friendsid;
    }

    public void setFriendsid(Long friendsid) {
        this.friendsid = friendsid;
    }

	@Override
	public String toString() {
		return "Friends [id=" + id + ", userid=" + userid + ", friendsid=" + friendsid + "]";
	}
}