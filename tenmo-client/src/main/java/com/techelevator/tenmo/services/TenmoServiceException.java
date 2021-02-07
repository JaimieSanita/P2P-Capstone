package com.techelevator.tenmo.services;

public class TenmoServiceException extends Exception {

	public TenmoServiceException(int rawStatusCode, String responseBodyAsString) {
		System.out.println(rawStatusCode + " : " + responseBodyAsString);
	}

}
