package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {

	private int transferId;
	private int transferFrom;
	private int transferTo;
	private int transferType;
	private int transferStatusId;
	private BigDecimal amount;
	
	public Transfer() {}
	
	public Transfer(int transferId, int transferFrom, int transferTo, int transferType, int transferStatus, BigDecimal transferAmount) {
		this.transferId = transferId;
		this.transferFrom = transferFrom;
		this.transferTo = transferTo;
		this.transferType = transferType;
		this.transferStatusId = transferStatus;
		this.amount = transferAmount;
	}


	public int getTransferId() {
		return transferId;
	}


	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}


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


	public int getTransferType() {
		return transferType;
	}


	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}


	public int getTransferStatus() {
		return transferStatusId;
	}


	public void setTransferStatus(int id) {
		this.transferStatusId = id;
	}


	public BigDecimal getTransferAmount() {
		return amount;
	}


	public void setTransferAmount(BigDecimal transferAmount) {
		this.amount = transferAmount;
	}
	
	
}
