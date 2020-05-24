package com.spring.accountservice.controller;

import com.spring.accountservice.entity.Account;
import com.spring.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable("id") String id){
        return ResponseEntity.ok(accountService.get(id));
    }

    @PostMapping
    public ResponseEntity<Account> save(@RequestBody Account account){
        return ResponseEntity.ok(accountService.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable("id") String id,
                                          @RequestBody Account account){
        return ResponseEntity.ok(accountService.update(id, account));
    }

    @DeleteMapping
    public void delete(String id){
        accountService.delete(id);
    }

}


