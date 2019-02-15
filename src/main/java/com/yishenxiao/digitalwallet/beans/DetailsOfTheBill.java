package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;
import java.util.Date;

public class DetailsOfTheBill implements Serializable{
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private String msg;
   private Date date;
   private String monetary;
   private String currency;
   private String headIcon;
   private String addr;
   private Double fee;
   private int state;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMonetary() {
		return monetary;
	}
	public void setMonetary(String monetary) {
		this.monetary = monetary;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "DetailsOfTheBill [msg=" + msg + ", date=" + date + ", monetary=" + monetary + ", currency=" + currency
				+ ", headIcon=" + headIcon + ", addr=" + addr + ", fee=" + fee + ", state=" + state + "]";
	}
}
