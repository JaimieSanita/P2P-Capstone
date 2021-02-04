package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;


@Component
public class TransferSqlDAO implements TransferDAO {

	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Transfer sendTransfer(Transfer transfer, BigDecimal TEBucks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> listApprovedTransfers() {
		List<Transfer> approvedTransfers = new ArrayList<>();
		String query = "SELECT * FROM transfers AS t "+
						"JOIN transfer_statuses AS ts ON t.transfer_status_id = ts.transfer_status_id "+
						"WHERE ts.transfer_status_id = ?";
		
		int transferStatusId = 2;
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, transferStatusId);
		while(results.next()) {
			Transfer transfer = this.mapRowToTransfer(results);
			approvedTransfers.add(transfer);
		}
		return approvedTransfers;
	}

	@Override
	public Transfer getTransferById(int id) {
		Transfer transfer = null;
		String query = "SELECT * FROM transfer WHERE transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
		if(results.next()) {
			transfer = mapRowToTransfer(results);
		}
		return transfer;
	}

	private Transfer mapRowToTransfer(SqlRowSet results)  {
		Transfer transfer = new Transfer();
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferType(results.getString("transfer_type_id"));
		transfer.setTransferStatus(results.getInt("transfer_status_id"));
		transfer.setTransferFrom(results.getString("account_from"));
		transfer.setTransferTo(results.getString("account_to"));
		transfer.setTransferBalance(results.getBigDecimal("amount"));
		
		return transfer;
		
	}
	
	
	
}
