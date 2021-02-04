package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.AccountBalance;

@Component
public class AccountBalanceSqlDAO implements AccountBalanceDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	
	public AccountBalanceSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public AccountBalance getBalance(int userId) {
		AccountBalance ab = null;
		String query = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet results = this.jdbcTemplate.queryForRowSet(query, userId);
		
		if(results.next()) {
			ab = this.mapRowToAccount(results);
			
		}
		return ab;
	}

	
	
	//we need to figure out constructor relationship here
	//do we need an empty constructor maybe?
	//
	private AccountBalance mapRowToAccount(SqlRowSet results) {
		
		AccountBalance ab = new AccountBalance();
		ab.setAccountId(results.getInt("account_id"));
		ab.setUserId(results.getInt("user_id"));
		ab.setBalance(results.getBigDecimal("balance"));
		
		return ab;
		
	}
	
	
	
}
