package com.yishenxiao.commons.beans.easemob;

import java.util.List;
import java.util.Map;

import io.swagger.client.model.MsgContent;

public class ImageMsgContent {
    private String target_type;
    private List<String> target;
    private Map<String, Object> msg;
    private String from;
    private Map<String, Object> size;
    
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

	public Map<String, Object> getSize() {
		return size;
	}

	public void setSize(Map<String, Object> size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ImageMsgContent [target_type=" + target_type + ", target=" + target + ", msg=" + msg + ", from=" + from
				+ ", size=" + size + "]";
	}  
}  
