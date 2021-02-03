package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	
	Transfer sendTransfer(Transfer transfer, BigDecimal TEBucks);
	
	List<Transfer> listUserTransfers();
	
	Transfer getTransferById(int id);
	
	
}
