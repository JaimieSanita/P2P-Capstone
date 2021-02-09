package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.AccountBalance;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferRequest;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.services.TransferService;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;
	private TransferService transferService;
	private AuthenticatedUser currentUser;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);

	}
	
	public void setCurrentUser(AuthenticatedUser currentUser) {
		this.currentUser = currentUser;
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***"
					+ System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt + ": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt + ": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch (NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while (result == null);
		return result;
	}

	// Use Case 3
	public void printCurrentAccountBalance(AccountBalance accountBalance) {
		out.println("Your current account balance is: " + accountBalance.getBalance());
	}


	// Use Case 6
	public void printTransferDetails(Transfer transfer) {
		out.println("--------------------------------------------");
		out.println("Transfer Details");
		out.println("--------------------------------------------");
		out.println(transfer.getTransferId());
		out.println(transfer.getTransferFrom());
		out.println(transfer.getTransferTo());
		out.println(transfer.getTransferType());
		out.println(transfer.getTransferStatus());
		out.println(transfer.getTransferAmount());
	}

	// TODO printTransfer method that loops through transfers and prints them one by
	// one-- returning a transfer array

	public void printTransfers(Transfer[] transfers) {
		if (transfers != null) {
			out.println("------------------------------------------------");
			out.println("Transfers");
			out.println("ID\t\t " + "From/To\t\t " + " Amount");
			out.println("------------------------------------------------");
			for (Transfer transfer : transfers) {
				out.println(transfer.getTransferId() + "\t\t" + "From: " + transfer.getTransferFrom() + "\t\t $"
						+ transfer.getTransferAmount());

			}
		}
	}
	
	public void printTransfersBetter(Transfer[] transfers) {
		if (transfers != null) {
			out.println("------------------------------------------------");
			out.println("Transfers");
			out.println("ID\t\t " + "From/To\t\t " + " Amount");
			out.println("------------------------------------------------");
			String fromOrTo = "";
			int name = 0;
			for (Transfer transfer : transfers) {
				if(this.currentUser.getUser().getId() == transfer.getTransferFrom()) {
					fromOrTo = "From: ";
					name = transfer.getTransferFrom();
				} if(this.currentUser.getUser().getId() == transfer.getTransferTo()) {
					fromOrTo = "To: ";
					name = transfer.getTransferTo();
				}
				out.println(transfer.getTransferId() + "\t\t" + fromOrTo + name + "\t\t$" + transfer.getTransferAmount());
			}
			out.println("---------");
			int transferSelection = getUserInputInteger("Please enter transfer ID to view details (0 to cancel)");
			for(Transfer transfer : transfers) {
			if(transferSelection == transfer.getTransferId()) {
				out.println("--------------------------------------------");
				out.println("Transfer Details");
				out.println("--------------------------------------------");
				out.println("ID:" + transfer.getTransferId());
				out.println("From: " + transfer.getTransferFrom());
				out.println("To: " + transfer.getTransferTo());
				if(transfer.getTransferType() == 2) {
					out.println("Type: Send");
				} else {
					out.println("Type: Request");
				}
				if(transfer.getTransferStatus() == 2) {
					out.println("Status: Approved");
				} else if (transfer.getTransferStatus() == 1) {
					out.println("Status: Pending");
				} else {
					out.println("Status: Rejected");
				}
				out.println("Amount" + transfer.getTransferAmount());
			}
			}
		}
	}

	public void printUsers(User[] user) {
		out.println("-------------------------------------------");
		out.println("Users");
		out.println("ID\t\t\t" + " Name");
		out.println("-------------------------------------------");
		for (User users : user) {
			out.println(users.getId() + "\t\t\t" + users.getUsername());
		}

	}
}
