package com.microservice.accountservice.service;

import com.microservice.accountservice.entity.AccountEntity;
import com.microservice.accountservice.repo.AccountRepository;
import com.microservice.client.dto.AccountResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountResource getSingleAccount(String id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(accountEntity, AccountResource.class);
    }

    @Override
    public Slice<AccountResource> getAllAccounts(Pageable pageable) {
        Slice<AccountEntity> accounts = accountRepository.findAll(pageable);
        return accounts.map(accountEntity -> {
            AccountResource accountResource = new AccountResource();
            accountResource.setId(accountEntity.getId());
            accountResource.setName(accountEntity.getName());
            accountResource.setSurname(accountEntity.getSurname());
            accountResource.setUsername(accountEntity.getUsername());
            accountResource.setBirthDate(accountEntity.getBirthDate());
            accountResource.setEmail(accountEntity.getEmail());
            return accountResource;
        });
    }

    @Override
    @Transactional
    public AccountResource createAccount(AccountResource accountResource) {
        AccountEntity savedAccountEntity = modelMapper.map(accountResource, AccountEntity.class);
        savedAccountEntity = accountRepository.save(savedAccountEntity);
        accountResource.setId(savedAccountEntity.getId());
        return accountResource;
    }

    @Override
    @Transactional
    public AccountResource updateAccount(String id, AccountResource accountResource) {
        Assert.notNull(id, "id cannot be null");
        Optional<AccountEntity> account = accountRepository.findById(id);
        AccountEntity accountToUpdate = account.map(entity -> {
            entity.setBirthDate(accountResource.getBirthDate());
            entity.setName(accountResource.getName());
            entity.setSurname(accountResource.getSurname());
            return entity;
        }).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(accountRepository.save(accountToUpdate), AccountResource.class);
    }

    @Override
    @Transactional
    public void deleteAccount(String id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        accountRepository.delete(accountEntity);
    }

}
