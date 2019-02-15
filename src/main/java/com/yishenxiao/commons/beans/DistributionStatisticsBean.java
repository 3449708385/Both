package com.yishenxiao.commons.beans;

import java.io.Serializable;

public class DistributionStatisticsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private int m1;
    private int m2;
    private int m3;
    private int m4;
    private int m5;
    private int m6;
    private String inviteCode;
    private String bothPoint;
    private long created;
    private String inviteDoc;
    private String inviteUrl;
    
	public int getM1() {
		return m1;
	}
	public void setM1(int m1) {
		this.m1 = m1;
	}
	public int getM2() {
		return m2;
	}
	public void setM2(int m2) {
		this.m2 = m2;
	}
	public int getM3() {
		return m3;
	}
	public void setM3(int m3) {
		this.m3 = m3;
	}
	public int getM4() {
		return m4;
	}
	public void setM4(int m4) {
		this.m4 = m4;
	}
	public int getM5() {
		return m5;
	}
	public void setM5(int m5) {
		this.m5 = m5;
	}
	public int getM6() {
		return m6;
	}
	public void setM6(int m6) {
		this.m6 = m6;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getBothPoint() {
		return bothPoint;
	}
	public void setBothPoint(String bothPoint) {
		this.bothPoint = bothPoint;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public String getInviteDoc() {
		return inviteDoc;
	}
	public void setInviteDoc(String inviteDoc) {
		this.inviteDoc = inviteDoc;
	}
	public String getInviteUrl() {
		return inviteUrl;
	}
	public void setInviteUrl(String inviteUrl) {
		this.inviteUrl = inviteUrl;
	}
	@Override
	public String toString() {
		return "DistributionStatisticsBean [m1=" + m1 + ", m2=" + m2 + ", m3=" + m3 + ", m4=" + m4 + ", m5=" + m5
				+ ", m6=" + m6 + ", inviteCode=" + inviteCode + ", bothPoint="
				+ bothPoint + ", created=" + created + ", inviteDoc=" + inviteDoc + ", inviteUrl=" + inviteUrl + "]";
	}
}
