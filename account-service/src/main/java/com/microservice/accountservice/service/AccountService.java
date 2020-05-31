package com.microservice.accountservice.service;

import com.microservice.client.dto.AccountResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AccountService {
    AccountResource getSingleAccount(String id);
    Slice<AccountResource> getAllAccounts(Pageable pageable);
    AccountResource createAccount(AccountResource account);
    AccountResource updateAccount(String id, AccountResource account);
    void deleteAccount(String id);
}
