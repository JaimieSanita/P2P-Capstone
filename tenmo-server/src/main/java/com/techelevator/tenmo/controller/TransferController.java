package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
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
import com.techelevator.tenmo.model.TransferRequest;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

	private UserDAO userDAO;
	private TransferDAO transferDAO;
	private UserSqlDAO userSqlDAO;

	public TransferController(TransferDAO transferDAO, UserDAO userDAO) {
		this.userDAO = userDAO;
		this.transferDAO = transferDAO;
	}

	@RequestMapping(path = "/transfer", method = RequestMethod.POST)
	public Transfer sendTransfer(@RequestBody TransferRequest request, Principal principal) throws Exception {
		String username = principal.getName();
		int userId = this.userDAO.findIdByUsername(username);
		if (userId != request.getTransferFrom()) {
			// throw custom exception here
			throw new Exception("You don't have permission to make this transfer");
		}

		return this.transferDAO.sendTransfer(request);

	}

	@RequestMapping(path = "/transfer", method = RequestMethod.GET)
	public List<Transfer> listTransfers(Principal principal) {

		String username = principal.getName();
		int userId = this.userDAO.findIdByUsername(username);

		return this.transferDAO.listTransfers(userId);

	}

	@RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
	public Transfer getTransferById(@PathVariable int transferId) {

		return transferDAO.getTransferById(transferId);
	}


}
