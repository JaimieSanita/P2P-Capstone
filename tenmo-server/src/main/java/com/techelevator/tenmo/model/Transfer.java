package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

	private int transferId;
	private String transferFrom;
	private String transferTo;
	private String transferType;
	private int transferStatusId;
	private BigDecimal transferBalance;
	
	public Transfer() {}
	
	public Transfer(int transferId, String transferFrom, String transferTo, String transferType, int transferStatus, BigDecimal transferBalance) {
		this.transferId = transferId;
		this.transferFrom = transferFrom;
		this.transferTo = transferTo;
		this.transferType = transferType;
		this.transferStatusId = transferStatus;
		this.transferBalance = transferBalance;
	}


	public int getTransferId() {
		return transferId;
	}


	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}


	public String getTransferFrom() {
		return transferFrom;
	}


	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}


	public String getTransferTo() {
		return transferTo;
	}


	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}


	public String getTransferType() {
		return transferType;
	}


	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}


	public int getTransferStatus() {
		return transferStatusId;
	}


	public void setTransferStatus(int id) {
		this.transferStatusId = id;
	}


	public BigDecimal getTransferBalance() {
		return transferBalance;
	}


	public void setTransferBalance(BigDecimal transferBalance) {
		this.transferBalance = transferBalance;
	}
	
	
}
