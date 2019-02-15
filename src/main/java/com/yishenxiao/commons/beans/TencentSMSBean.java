package com.yishenxiao.commons.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class TencentSMSBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int appid;//腾讯appid
    private String appkey;//腾讯appkey
    private int type;//0:普通短信;1:营销短信
    private int templId;//业务在控制台审核通过的模板ID
    private String nationCode;//手机区号
    private ArrayList<String> phoneNumberList;//手机号List
    private ArrayList<String> params;//参数，分别对应上面假定模板的{1}，{2}，{3}
    private String sign;//app凭证，具体计算方式见下注
    private String extend;//通道扩展码，可选字段，默认没有开通(需要填空)
    private String ext;//用户的session内容，腾讯server回包中会原样返回，可选字段，不需要就填空。
	public int getAppid() {
		return appid == 0 ? 1400051923 : appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public String getAppkey() {
		return appkey == null ? "46fb066661ebb2d3a3504bd997fb68e8" : appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTemplId() {
		return templId == 0 ? 60980 : templId;
	}
	public void setTemplId(int templId) {
		this.templId = templId;
	}
	public String getNationCode() {
		return nationCode;
	}
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	public ArrayList<String> getPhoneNumberList() {
		return phoneNumberList;
	}
	public void setPhoneNumberList(ArrayList<String> phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}
	public ArrayList<String> getParams() {
		return params;
	}
	public void setParams(ArrayList<String> params) {
		this.params = params;
	}
	public String getSign() {
		return sign == null ? "" : sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getExtend() {
		return extend == null ? "" : extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getExt() {
		return ext == null ? "" : ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	@Override
	public String toString() {
		return "TencentSMSBean [appid=" + appid + ", appkey=" + appkey + ", type=" + type + ", templId=" + templId
				+ ", nationCode=" + nationCode + ", phoneNumberList=" + phoneNumberList + ", params=" + params
				+ ", sign=" + sign + ", extend=" + extend + ", ext=" + ext + "]";
	}
    
}
