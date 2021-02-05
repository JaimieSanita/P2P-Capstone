package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountBalance;
import com.techelevator.tenmo.model.TransferRequest;

public interface AccountBalanceDAO {

	AccountBalance getBalance(int userId);
	
	void addToBalance(TransferRequest transferRequest);
	
	void subtractFromBalance(TransferRequest transferRequest);
	
	AccountBalance  findAccountById(int userId);
}
