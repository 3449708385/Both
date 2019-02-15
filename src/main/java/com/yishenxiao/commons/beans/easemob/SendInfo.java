package com.yishenxiao.commons.beans.easemob;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SendInfo implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String target_type;//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
	private List<String> target;//注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，
	private Map<String, Object> msg;//"type" : "txt","msg" : "hello from rest"消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]里的bodies内容
	private String from;////表示消息发送者。无此字段Server会默认设置为"from":"admin"，有from字段但值为空串("")时请求失败
	private Map<String, Object> ext;//拓展参数
    private String headIcon;
    private String nickName;
	
	public String getTarget_type() {
		return target_type;
	}
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}
	public List<String> getTarget() {
		return target;
	}
	public void setTarget(List<String> target) {
		this.target = target;
	}
	public Map<String, Object> getMsg() {
		return msg;
	}
	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Map<String, Object> getExt() {
		return ext;
	}
	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "SendInfo [target_type=" + target_type + ", target=" + target + ", msg=" + msg + ", from=" + from
				+ ", ext=" + ext + ", headIcon=" + headIcon + ", nickName=" + nickName + "]";
	}
    
}
