package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRequest;


@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	@Override
	public Transfer sendTransfer(Transfer transfer, BigDecimal TEBucks) {
		// TODO Auto-generated method stub
		return null;
	}
	
	*/
	@Override
	public List<Transfer> listTransfers(int userId) {
		List<Transfer> approvedTransfers = new ArrayList<>();
		String query = "SELECT * " + 
						"FROM users AS u " + 
						"JOIN accounts AS a ON u.user_id = a.user_id " + 
						"JOIN transfers AS t ON a.account_id = t.account_from " + 
						"WHERE u.user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, userId);
		while(results.next()) {
			Transfer transfer = this.mapRowToTransfer(results);
			approvedTransfers.add(transfer);
		}
		return approvedTransfers;
	}
	

	@Override
	public Transfer getTransferById(int transferId) {
		Transfer transfer = null;
	
		String query = "SELECT * FROM users AS u " + 
				"JOIN accounts AS a ON u.user_id = a.user_id " + 
				"JOIN transfers AS t ON a.account_id = t.account_from " + 
				"WHERE t.transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, transferId);
		if(results.next()) {
			transfer = mapRowToTransfer(results);
		}
		
		return transfer;
		
	}
	
	
	
	

	private Transfer mapRowToTransfer(SqlRowSet results)  {
		Transfer transfer = new Transfer();
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferType(results.getInt("transfer_type_id"));
		transfer.setTransferStatus(results.getInt("transfer_status_id"));
		transfer.setTransferFrom(results.getInt("account_from"));
		transfer.setTransferTo(results.getInt("account_to"));
		transfer.setTransferAmount(results.getBigDecimal("amount"));
		
		return transfer;
		
	}

	@Override
	public Transfer sendTransfer(TransferRequest transferRequest, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
	
}
