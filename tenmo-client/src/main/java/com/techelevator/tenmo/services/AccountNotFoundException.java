package com.techelevator.tenmo.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Transfer not found.")
public class AccountNotFoundException extends Exception {
	private static final long serialVersionUID=1L;
	
	
	public AccountNotFoundException(String message) {
		super(message);
	}
}
