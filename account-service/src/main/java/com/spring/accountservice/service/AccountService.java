package com.spring.accountservice.service;

import com.spring.accountservice.entity.Account;

import java.util.List;

public interface AccountService {
    Account get(String id);
    List<Account> getAll();
    Account save(Account account);
    Account update(String id, Account account);
    void delete(String id);
}
