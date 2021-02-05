package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.AccountBalance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;

@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;
	private AccountBalanceDAO accountBalanceDAO;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate, AccountBalanceDAO accountBalanceDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.accountBalanceDAO = accountBalanceDAO;
	}

	@Override
	public Transfer sendTransfer(TransferRequest transferRequest) throws Exception {

		Transfer transfer = null;

		if (transferRequest.getTransferFrom() == transferRequest.getTransferTo()) {
			System.out.println("You cannot send a transfer to yourself. Please select a valid user.");
			return transfer;
		}

		BigDecimal transferAmount = transferRequest.getAmount();
		int transferFrom = transferRequest.getTransferFrom();

		//
//		}
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		try {
			con.setAutoCommit(false);
			AccountBalance accountBalance = accountBalanceDAO.getBalance(transferFrom);
			BigDecimal currentBalance = accountBalance.getBalance();

			if (transferAmount.compareTo(currentBalance) == -1 && transferAmount.compareTo(new BigDecimal(0)) == 1) {
				String query = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
						+ "VALUES( 2,2, ?,?,?) RETURNING *";
				SqlRowSet results = this.jdbcTemplate.queryForRowSet(query, transferRequest.getTransferFrom(),
						transferRequest.getTransferTo(), transferRequest.getAmount());
				accountBalanceDAO.addToBalance(transferRequest);
				accountBalanceDAO.subtractFromBalance(transferRequest);

				if (results.next()) {
					transfer = this.mapRowToTransfer(results);

				}

				con.commit();
				return transfer;
			}
		} catch (Exception ex) {
			con.rollback();
			throw ex;
		} finally {
			con.setAutoCommit(true);
		}
		return transfer;

	}

	@Override
	public List<Transfer> listTransfers(int userId) {
		List<Transfer> approvedTransfers = new ArrayList<>();
		String query = "SELECT * " + "FROM users AS u " + "JOIN accounts AS a ON u.user_id = a.user_id "
				+ "JOIN transfers AS t ON a.account_id = t.account_from " + "WHERE u.user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(query, userId);
		while (results.next()) {
			Transfer transfer = this.mapRowToTransfer(results);
			approvedTransfers.add(transfer);
		}
		return approvedTransfers;
	}

	@Override
	public Transfer getTransferById(int transferId) {
		Transfer transfer = null;

		String query = "SELECT * FROM users AS u " + "JOIN accounts AS a ON u.user_id = a.user_id "
				+ "JOIN transfers AS t ON a.account_id = t.account_from " + "WHERE t.transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, transferId);
		if (results.next()) {
			transfer = mapRowToTransfer(results);
		}

		return transfer;

	}

	private Transfer mapRowToTransfer(SqlRowSet results) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferType(results.getInt("transfer_type_id"));
		transfer.setTransferStatus(results.getInt("transfer_status_id"));
		transfer.setTransferFrom(results.getInt("account_from"));
		transfer.setTransferTo(results.getInt("account_to"));
		transfer.setTransferAmount(results.getBigDecimal("amount"));

		return transfer;

	}

}
