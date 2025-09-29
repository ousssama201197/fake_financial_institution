package com.bank.fake_financial_institution.controller;

import com.bank.fake_financial_institution.dto.AccountDTO;
import com.bank.fake_financial_institution.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Get all accounts and balance by customer number with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful"),
    })
    public ResponseEntity<Page<AccountDTO>> getAccountsByCustomer(
            @RequestParam String customerNumber,
            Pageable pageable) {
        Page<AccountDTO> accounts = accountService.findAll(pageable, customerNumber);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    @Operation(summary = "Create a new account for a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully"),
    })
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO created = accountService.create(accountDTO);
        return ResponseEntity.ok(created);
    }
}

