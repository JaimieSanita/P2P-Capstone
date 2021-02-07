package com.techelevator.tenmo.services;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.AccountBalance;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferRequest;


public class TransferService {
	private String BASE_URL;

	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser currentUser;

	// do we need this?
	// private ConsoleService consoleService = new ConsoleService(input, output);
	public void setCurrentUser(AuthenticatedUser currentUser) {
		this.currentUser = currentUser;
	}

	public TransferService(String url) {
		this.BASE_URL = url;

	}

	// TODO map out methods to correspond to our controller methods
	// transfer entity helper method
	// no print lines here
	//

	public Transfer[] listTransfers() throws RestClientResponseException {
		Transfer[] output = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "transfer";

			output = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, Transfer[].class).getBody();
		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}
		return output;
	}

	public Transfer getTransferById(int transferId) throws RestClientResponseException {
		Transfer transfer = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "transfer/" + transferId;

			transfer = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, Transfer.class).getBody();
		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}

		return transfer;
	}

	public Transfer sendBucks(TransferRequest request) throws RestClientResponseException {
		Transfer transfer = null;

		try {
			// TODO make a httpentity with <TransferRequest> in it
			// TODO use post for object/exchange and expect a Transfer back
			HttpEntity<TransferRequest> entity = this.transferRequestEntity(request);
			transfer = restTemplate.exchange(BASE_URL + "transfer", HttpMethod.POST, entity, Transfer.class).getBody();

		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}
		return transfer;

	}

	public AccountBalance getBalance(int id) throws RestClientResponseException {
		AccountBalance balance = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "balance";
			balance = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, AccountBalance.class)
					.getBody();
		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}

		return balance;
	}
	
	public Transfer pendingRequests(TransferRequest transferRequest) throws RestClientResponseException {
		Transfer transfer = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "transfer";
			transfer = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, Transfer.class)
					.getBody();
		} catch (RestClientResponseException ex) {
			ex.printStackTrace();
		}

		return transfer;
	}

	public Transfer requestTEBucks(TransferRequest request) throws RestClientResponseException {
		Transfer transfer = null;
		
		try {
			HttpEntity<TransferRequest> entity = this.transferRequestEntity(request);
			transfer = restTemplate.exchange(BASE_URL + "transfer", HttpMethod.POST, entity, Transfer.class).getBody();

		} catch (RestClientResponseException ex) {
			System.out.println("Unable to make your transfer, try again later");
			ex.printStackTrace();
		}
		return transfer;

	}

	//	private HttpEntity<AccountBalance> makeAccountBalanceEntity(AccountBalance accountBalance) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setBearerAuth(this.currentUser.getToken());
//		HttpEntity<AccountBalance> accountBalanceEntity = new HttpEntity<>(accountBalance, headers);
//		return accountBalanceEntity;
//	}

	private HttpEntity<TransferRequest> transferRequestEntity(TransferRequest transferRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(this.currentUser.getToken());
		HttpEntity<TransferRequest> transferRequestEntity = new HttpEntity<>(transferRequest, headers);
		return transferRequestEntity;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		if (currentUser != null) {
			headers.setBearerAuth(currentUser.getToken());
		}
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
