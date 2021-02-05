package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountBalanceDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.AccountBalance;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountBalanceController {

	private AccountBalanceDAO accountBalanceDAO;
	private UserDAO userDAO;

	public AccountBalanceController(AccountBalanceDAO accountBalanceDAO, UserDAO userDAO) {
		this.accountBalanceDAO = accountBalanceDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
	public AccountBalance getBalance(@PathVariable int id) {

		AccountBalance ab = accountBalanceDAO.getBalance(id);
		return ab;

	}
}