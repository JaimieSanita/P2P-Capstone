package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class AccountBalance {

	private int userId;
	private int accountId;
	private BigDecimal balance;
	
	public AccountBalance() { }
	
	public AccountBalance(int userId, int accountId, BigDecimal balance) {
		this.userId = userId;
		this.accountId = accountId;
		this.balance = balance;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
