package com.account.accountcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.account.entity.Account;
import com.account.service.AccountService;

import jakarta.transaction.Transaction;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create/{customerId}")
    public ResponseEntity<Account> createAccount(@PathVariable String customerId) {
        return ResponseEntity.ok(accountService.createAccount(customerId));
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(account));
    }

    @DeleteMapping("/close/{accountNumber}")
    public ResponseEntity<String> closeAccount(@PathVariable String accountNumber) {
        accountService.closeAccount(accountNumber);
        return ResponseEntity.ok("Account closed successfully");
    }

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> getAccountBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountBalance(accountNumber));
    }

    @GetMapping("/statements/{accountNumber}")
    public ResponseEntity<List<Transaction>> getAccountStatements(
            @PathVariable String accountNumber,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(accountService.getAccountStatements(accountNumber, startDate, endDate));
    }

    // Other account-related API endpoints...

}
