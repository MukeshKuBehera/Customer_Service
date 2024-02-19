package com.account.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.entity.Account;
import com.account.entity.Customer;
import com.account.repo.AccountRepository;

import jakarta.transaction.Transaction;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Account createAccount(String customerId) {
		//retrieve customer from database
		Customer customer=accountRepository.findByCustomerId(customerId);
		if (customer == null) {
            throw new IllegalArgumentException("Customer not found for customerId: " + customerId);
        }
		
		Account account=new Account();
		// Set initial balance
		account.setBalance(0.0);
	
		// Generate account number
		String accountNumber = generateAccountNumber();
		account.setAccountNumber(accountNumber);
		
		

		// Save the account to the database
		return accountRepository.save(account);
	}

	private String generateAccountNumber() {
		// Auto-generated method stub
		return "ACC" + (int) (Math.random() * 100000000);
	}

	public Account updateAccount(Account account) {
		// Business logic for updating account information
		// Update balance, customer association, etc.

		// Save the updated account to the database
		return accountRepository.save(account);
	}

	public void closeAccount(String accountNumber) {
		// Business logic for closing an account
		// Perform necessary cleanup, mark account as closed, etc.

		// Delete the account from the database
		accountRepository.deleteById(accountNumber);
	}

	public double getAccountBalance(String accountNumber) {
		// Business logic for getting the account balance
		// Retrieve the account from the database and return the balance
		/*
		 * accountRepository.findById(accountNumber) .map(Account::getBalance)
		 * .orElseThrow(() -> new IllegalArgumentException("Account not found")); return
		 * 000;
		 */
		return 00;
	}

	public List<Transaction> getAccountStatements(String accountNumber, Date startDate, Date endDate) {
		return null;
		// Business logic for retrieving account statements within a date range
		// You may have a Transaction entity representing debit and credit transactions

		// Retrieve transactions from the database based on account number and date
		// range
		// Return a list of transactions
		// You might want to implement a TransactionRepository for this purpose

		// Example (assuming you have a TransactionRepository):
		// List<Transaction> transactions =
		// transactionRepository.findByAccountNumberAndDateBetween(accountNumber,
		// startDate, endDate);
		// return transactions;
	}

	// Additional account-related operations...

	
}
