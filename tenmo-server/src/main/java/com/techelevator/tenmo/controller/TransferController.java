package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techelevator.tenmo.model.AccountBalance;
import com.techelevator.tenmo.model.Transfer;

public class TransferController {
	
	
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/balance", method = RequestMethod.GET)
public AccountBalance getBalance() {
AccountBalance ab = new AccountBalance();
	
	
	return  ab;
		
}

@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/transfer", method = RequestMethod.POST)
public Transfer sendTransfer(@RequestBody Transfer transfer, BigDecimal TEBucks) {
	
	
	
	Transfer transfers = new Transfer();
	
	return transfers;
	
}


@PreAuthorize("isAuthenticated()")
@RequestMapping(path="/transfer", method = RequestMethod.GET)
public List<Transfer> listUserTransfers() {
	List<Transfer> list = new ArrayList<>();
	
	
	return list;

}

@PreAuthorize("isAuthenticated()")
@RequestMapping(path= "/transfer/{id}", method = RequestMethod.GET)
public Transfer getTransferById(@PathVariable int id) {

	Transfer transfers = new Transfer();
	
	return transfers;
}








}
