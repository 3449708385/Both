package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.List;

public class UserGagEasemobBean implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<String> usernames;
    private Integer mute_duration;
	public List<String> getUsernames() {
		return usernames;
	}
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	public Integer getMute_duration() {
		return mute_duration;
	}
	public void setMute_duration(Integer mute_duration) {
		this.mute_duration = mute_duration;
	}
	@Override
	public String toString() {
		return "UserGagEasemobBean [usernames=" + usernames + ", mute_duration=" + mute_duration + "]";
	} 
}
