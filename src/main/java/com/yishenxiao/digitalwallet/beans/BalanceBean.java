package com.yishenxiao.digitalwallet.beans;

import java.io.Serializable;

public class BalanceBean implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Double balance;
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "BalanceBean [balance=" + balance + "]";
	}
}
