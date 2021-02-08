package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.AccountBalance;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferRequest;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.services.TransferService;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;
	private TransferService transferService;
	
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
	public void printSendTEBucks(TransferRequest transfer) {
		out.println("-------------------------------------------");
		out.println("Users");
		out.println("ID\t\t\t" + " Name");
		out.println("-------------------------------------------");
		//while(findAllUsers() != null) {
			//Print each user
			//getUserInput(String prompt)
			//getUserInputInteger(String prompt)
			
		//}
		out.println("---------");
		out.println("---------");
		out.println("Please enter transfer ID to view details (0 to cancel): ");
	
		}
	
	//Use Case 5
	public void printViewTransfers(Transfer[] transfers, int currentUserId) {
		out.println("-------------------------------------------");
		out.println("Transfers");
		out.println("ID\t\t\t "  + "From/To\t\t\t\t\t " + " Amount");
		out.println("-------------------------------------------");
		//while loop to represent actual transfer amount
		
		int length = transfers.length;
		int count = 0;

		
		
		while(count < length) {
			Transfer transfer = transfers[count];
			if (transfer.getTransferFrom() == currentUserId) {
							
	//How to get the username using transferTo field in the transfer class? i.e. transfer.getTransferTo()
			System.out.println(transfer.getTransferId() + "/t/t/t " + "To: ?" + "/t/t/t/t/t " + " Amount");
																	//   ? ^
			} else {
				System.out.println(transfer.getTransferId() + "/t/t/t " + "From: ?" + "/t/t/t/t/t " + transfer.getTransferAmount());
			}
			count++;
		}
		//out.println("Please enter " + transfer.getTransferTo() + " of" + transfer.getTransferTo() + " you are sending to (0 to cancel): ");
		//out.println("Enter " + transfer.getTransferAmount());
	

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
	
	public void printTransfers(Transfer[] transfers) {
		if(transfers != null) {
			out.println("--------------------------------------------");
			out.println("Transfers");
			out.println("ID\t\t\t "  + "From/To\t\t\t\t\t " + " Amount");
			out.println("-------------------------------------------");
			for(Transfer transfer : transfers) {
				out.println(transfer.getTransferId() + "\t\t" + transfer.getTransferFrom() + "\t\t" + transfer.getTransferAmount());
			}
		}
	}
		public void printUsers(User[] user) {
			out.println("-------------------------------------------");
			out.println("-------------------------------------------");
			out.println("Users");
			out.println("ID\t\t\t" + " Name");
			out.println("-------------------------------------------");
			for(User users : user) {
				out.println(users.getId() + "\t\t\t" + users.getUsername());
			}
			
		
		}
	}
	

