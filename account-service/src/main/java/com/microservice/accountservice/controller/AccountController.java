package com.microservice.accountservice.controller;

import com.microservice.accountservice.service.AccountService;
import com.microservice.client.dto.AccountResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Slice<AccountResource>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResource> getSingleAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(accountService.getSingleAccount(id));
    }

    @PostMapping
    public ResponseEntity<AccountResource> createAccount(@RequestBody AccountResource accountResource) {
        return ResponseEntity.ok(accountService.createAccount(accountResource));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResource> updateAccount(@PathVariable("id") String id,
                                                         @RequestBody AccountResource accountResource) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") String id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

}


