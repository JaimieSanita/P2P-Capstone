package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class TransferRequest {
	private int transferFrom;
	private int transferTo;
	private BigDecimal amount;
	
	public int getTransferFrom() {
		return transferFrom;
	}
	public void setTransferFrom(int transferFrom) {
		this.transferFrom = transferFrom;
	}
	public int getTransferTo() {
		return transferTo;
	}
	public void setTransferTo(int transferTo) {
		this.transferTo = transferTo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
