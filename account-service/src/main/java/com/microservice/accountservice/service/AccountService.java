package com.microservice.accountservice.service;

import com.microservice.client.dto.AccountResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AccountService {
    AccountResource get(String id);
    Slice<AccountResource> getAll(Pageable pageable);
    AccountResource save(AccountResource account);
    AccountResource update(String id, AccountResource account);
    void delete(String id);
}
