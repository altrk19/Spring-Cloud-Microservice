package com.spring.accountservice.controller;

import com.spring.accountservice.dto.AccountResource;
import com.spring.accountservice.service.AccountService;
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
    public ResponseEntity<Slice<AccountResource>> getAll(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResource> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(accountService.get(id));
    }

    @PostMapping
    public ResponseEntity<AccountResource> save(@RequestBody AccountResource accountResource) {
        return ResponseEntity.ok(accountService.save(accountResource));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResource> update(@PathVariable("id") String id,
                                                  @RequestBody AccountResource accountResource) {
        return ResponseEntity.ok(accountService.update(id, accountResource));
    }

    @DeleteMapping
    public void delete(String id) {
        accountService.delete(id);
    }

}


