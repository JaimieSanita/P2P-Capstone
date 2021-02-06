package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import com.techelevator.tenmo.models.AccountBalance;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.services.TransferService;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
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
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
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
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while(result == null);
		return result;
	}
	
	//Use Case 3
	public void printCurrentAccountBalance(AccountBalance accountBalance) {
		out.println("Your current account balance is: " + accountBalance.getBalance());
	}
	
	//Use Case 4
	public void printSendTEBucks(Transfer transfer) {
		out.println("-------------------------------------------");
		out.println(transfer.getTransferId() + " Name");
		out.println("-------------------------------------------");
		//while loop
		out.println("---------");
		out.println("Enter " + transfer.getTransferId() + " of" + transfer.getTransferTo() + " you are sending to (0 to cancel) ");
		out.println("Enter " + transfer.getTransferAmount());
	}
	
	//Use Case 5
	public void printViewTransfers(Transfer transfer) {
		out.println("-------------------------------------------");
	}
	
	
	
	
	
	
	
	

	//Use Case 6
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
	
	//TODO printTransfer method that loops through transfers and prints them one by one-- returning a transfer array
	
	
	
}
