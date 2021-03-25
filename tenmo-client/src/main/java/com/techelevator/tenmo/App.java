package com.techelevator.tenmo;

import java.math.BigDecimal;

import com.techelevator.tenmo.models.AccountBalance;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferRequest;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountNotFoundException;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferNotFoundException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private TransferService transferService;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.transferService = new TransferService(API_BASE_URL);
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {

		try {
			console.printCurrentAccountBalance(this.transferService.getBalance());
		} catch (NullPointerException e) {
			System.out.println("Account balance can not be accessed.");
		}

	}

	private void viewTransferHistory() {
		Transfer[] transfers;
		try {
			transfers = transferService.listTransfers();
			this.console.printTransfersBetter(transfers);

		} catch (TransferNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void sendBucks() {
		User[] users;
		users = this.transferService.findAllUsers();
		this.console.printUsers(users);
		int userID = -1;
		do {
			userID = this.console.promptForUserID(users);
			if (userID == this.currentUser.getUser().getId()) {
				System.out.println("You cannot send money to yourself.");
			}
		} while (!(userID == 0 || userID != this.currentUser.getUser().getId()));
		if (userID == 0) {
			return;
		}

		int transferAmount = this.console.getUserInputInteger("Enter whole number. Do not include $");
		BigDecimal newAmount = BigDecimal.valueOf(transferAmount);
		BigDecimal currentBalance = this.transferService.getBalance().getBalance();
		if (newAmount.compareTo(new BigDecimal(0)) == -1) {
			System.out.println("You cannot transfer a negative amount. Please try again.");
		}
		else if (newAmount.compareTo(currentBalance) == 1) {
			System.out.println("You cannot transfer an amount greater than your account balance. Please try again.");
		} else {
			TransferRequest request = new TransferRequest();
			request.setAmount(newAmount);
			request.setTransferTo(userID);
			request.setTransferFrom(this.currentUser.getUser().getId());
			Transfer transfer;
			try {
				transfer = this.transferService.sendBucks(request);
				System.out.println("Your transfer is complete!");

			} catch (TransferNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;

		this.transferService.setCurrentUser(null);
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				// doing the login
				currentUser = authenticationService.login(credentials);
				// setting the current user onto our api service

				this.transferService.setCurrentUser(currentUser);
				this.console.setCurrentUser(currentUser);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}
}
