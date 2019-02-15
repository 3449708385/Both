package com.yishenxiao.digitalwallet.beans.postbean;

import java.io.Serializable;

public class AccountOperationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String purseAddress;
    private Double foreignAccount;
    private Double borrowFrom;
	public String getPurseAddress() {
		return purseAddress;
	}
	public void setPurseAddress(String purseAddress) {
		this.purseAddress = purseAddress;
	}
	public Double getForeignAccount() {
		return foreignAccount;
	}
	public void setForeignAccount(Double foreignAccount) {
		this.foreignAccount = foreignAccount;
	}
	public Double getBorrowFrom() {
		return borrowFrom;
	}
	public void setBorrowFrom(Double borrowFrom) {
		this.borrowFrom = borrowFrom;
	}
	@Override
	public String toString() {
		return "AccountOperationBean [purseAddress=" + purseAddress + ", foreignAccount=" + foreignAccount
				+ ", borrowFrom=" + borrowFrom + "]";
	}
}
