package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountBalanceDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.AccountBalance;
import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
	
/*private UserDAO userDAO;
private TransferDAO transferDAO;
private UserSqlDAO userSqlDAO;



public TransferController(TransferDAO transferDAO, UserDAO userDAO) {
	this.userDAO = userDAO;
	this.transferDAO = transferDAO;
}
*/


//Receiving a 401 Unauthorized error
@RequestMapping(path = "/transfer", method = RequestMethod.POST)
public Transfer sendTransfer(@RequestBody Transfer transfer, BigDecimal TEBucks) {
	
	Transfer transfer2 = new Transfer(4, "String transferFrom", "String transferTo", "String transferType", "String transferStatus",TEBucks);
	
	
	return transfer2;
	
}



@RequestMapping(path="/transfer", method = RequestMethod.GET)
public List<Transfer> listUserTransfers() {
	Transfer transfer = new Transfer(2, "String transferFrom", "String transferTo", "String transferType", "String transferStatus", BigDecimal.valueOf(1));
	List<Transfer> list = new ArrayList<>();
	list.add(transfer);
	
	return list;

}

@RequestMapping(path= "/transfer/{id}", method = RequestMethod.GET)
public Transfer getTransferById(@PathVariable int id) {

	Transfer transfer = new Transfer(1, "String transferFrom", "String transferTo", "String transferType", "String transferStatus", BigDecimal.valueOf(1));
	
	return transfer;
}


//TODO transfersqlDAO
//TODO accountBalanceSqlDAO





}
