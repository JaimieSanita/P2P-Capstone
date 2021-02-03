package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountBalanceDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.AccountBalance;


@RestController
@PreAuthorize("isAuthenticated()")
public class AccountBalanceController {

	/*
	private AccountBalanceDAO accountBalanceDAO;
	
	public AccountBalanceController(AccountBalanceDAO accountBalanceDAO) {
		this.accountBalanceDAO = accountBalanceDAO;
	}
	*/
	
	//Receiving a 401 Unauthorized error
	
	@RequestMapping(path = "/balance", method = RequestMethod.GET)
	public AccountBalance getBalance() {
		
		AccountBalance ab = new AccountBalance(3, 2, BigDecimal.ONE);
	
		
		
		return ab;
			

}
}