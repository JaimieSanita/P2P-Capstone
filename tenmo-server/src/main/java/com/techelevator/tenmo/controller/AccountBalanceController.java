package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountBalanceDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.AccountBalance;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountBalanceController {

	private AccountBalanceDAO accountBalanceDAO;
	private UserDAO userDAO;

	public AccountBalanceController(AccountBalanceDAO accountBalanceDAO, UserDAO userDAO) {
		this.accountBalanceDAO = accountBalanceDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(path = "/balance", method = RequestMethod.GET)
	public AccountBalance getMyBalance(Principal principal) {
		String username = principal.getName();
		int userId = this.userDAO.findIdByUsername(username);
		return this.accountBalanceDAO.getBalance(userId);
	}

	@RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
	public AccountBalance getBalance(@PathVariable int id) {

		AccountBalance ab = accountBalanceDAO.getBalance(id);
		return ab;

	}

	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> listOfUsers() {
		return userDAO.findAll();
	}

}