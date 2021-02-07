package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.techelevator.tenmo.model.Transfer;

import com.techelevator.tenmo.model.TransferRequest;

public interface TransferDAO {

	
	Transfer sendTransfer(TransferRequest transferRequest) throws Exception;
	
	List<Transfer> listTransfers(int userId);
	
	Transfer getTransferById( int transferId);
	
	
}
