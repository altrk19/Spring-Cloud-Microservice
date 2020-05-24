package com.spring.accountservice.service;

import com.spring.accountservice.entity.Account;
import com.spring.accountservice.repo.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account get(String id) {
        return accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(String id, Account account) {
        Assert.isNull(id, "id cannot be null");
        return accountRepository.save(account);
    }

    @Override
    public void delete(String id) {

    }

}
