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
import com.techelevator.tenmo.models.User;

public class TransferService {
	private String BASE_URL;

	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser currentUser;

	public void setCurrentUser(AuthenticatedUser currentUser) {
		this.currentUser = currentUser;
	}

	public TransferService(String url) {
		this.BASE_URL = url;

	}

	public Transfer[] listTransfers() throws TransferNotFoundException {
		Transfer[] output = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "transfer";

			output = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, Transfer[].class).getBody();
		} catch (RestClientResponseException ex) {
			if (ex.getRawStatusCode() == 404) {
				throw new TransferNotFoundException(ex.getRawStatusCode() + ": " + ex.getResponseBodyAsString());
			}
			throw ex;
		}
		return output;
	}

	public Transfer getTransferById(int transferId) throws TransferNotFoundException {
		Transfer transfer = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "transfer/" + transferId;

			transfer = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, Transfer.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new TransferNotFoundException(ex.getRawStatusCode() + ": " + ex.getResponseBodyAsString());

		}

		return transfer;
	}

	public Transfer sendBucks(TransferRequest request) throws TransferNotFoundException {
		Transfer transfer = null;

		try {
			HttpEntity<TransferRequest> entity = this.transferRequestEntity(request);
			transfer = restTemplate.exchange(BASE_URL + "transfer", HttpMethod.POST, entity, Transfer.class).getBody();

		} catch (RestClientResponseException ex) {
			throw new TransferNotFoundException(ex.getRawStatusCode() + ": " + ex.getResponseBodyAsString());
		}
		return transfer;

	}

	public AccountBalance getBalance() {
		AccountBalance balance = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "balance";
			balance = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, AccountBalance.class)
					.getBody();
		} catch (RestClientResponseException ex) {
			System.out.println("Error gettting balance.");
		}

		return balance;
	}

	public Transfer pendingRequests(TransferRequest transferRequest) throws RestClientResponseException {
		Transfer transfer = null;

		try {
			HttpEntity securityHeaders = makeAuthEntity();
			String requestUrl = BASE_URL + "transfer";
			transfer = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, Transfer.class).getBody();
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

	// Consider transferService Exceptions to be thrown after RestClient is caught
	public User[] findAllUsers() {
		User[] users = null;
		try {
			String requestUrl = BASE_URL + "users";
			HttpEntity securityHeaders = makeAuthEntity();
			users = restTemplate.exchange(requestUrl, HttpMethod.GET, securityHeaders, User[].class).getBody();

		} catch (RestClientResponseException ex) {
			System.out.println("We cannot show you all the users at this time.");
		}
		return users;
	}

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
