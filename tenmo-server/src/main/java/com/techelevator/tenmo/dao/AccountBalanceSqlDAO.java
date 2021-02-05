package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.AccountBalance;
import com.techelevator.tenmo.model.TransferRequest;

@Component
public class AccountBalanceSqlDAO implements AccountBalanceDAO {

	private JdbcTemplate jdbcTemplate;

	public AccountBalanceSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public AccountBalance getBalance(int userId) {
		AccountBalance ab = null;
		String query = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet results = this.jdbcTemplate.queryForRowSet(query, userId);

		if (results.next()) {
			ab = this.mapRowToAccount(results);

		}
		return ab;
	}

	@Override
	public void addToBalance(TransferRequest transferRequest) {
		// AccountBalance account = findAccountById(id);
		AccountBalance balance = this.findAccountById(transferRequest.getTransferFrom());

		BigDecimal newBalance = balance.getBalance().add(transferRequest.getAmount());
		String query = "UPDATE accounts SET balance = ? WHERE user_id = ? ";
		this.jdbcTemplate.update(query, newBalance, transferRequest.getTransferTo());

	}

	@Override
	public void subtractFromBalance(TransferRequest transferRequest) {

		AccountBalance balance = this.findAccountById(transferRequest.getTransferFrom());

		BigDecimal newBalance = balance.getBalance().subtract(transferRequest.getAmount());
		String query = "UPDATE accounts SET balance = ? WHERE user_id = ? ";
		this.jdbcTemplate.update(query, newBalance, transferRequest.getTransferFrom());

	}

	// we need to figure out constructor relationship here
	// do we need an empty constructor maybe?
	//
	private AccountBalance mapRowToAccount(SqlRowSet results) {

		AccountBalance ab = new AccountBalance();
		ab.setAccountId(results.getInt("account_id"));
		ab.setUserId(results.getInt("user_id"));
		ab.setBalance(results.getBigDecimal("balance"));

		return ab;

	}

	@Override
	public AccountBalance findAccountById(int userId) {
		AccountBalance account = null;
		String query = "SELECT * FROM accounts WHERE account_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, userId);
		if (results.next()) {
			account = mapRowToAccount(results);
		}
		return account;
	}

}
