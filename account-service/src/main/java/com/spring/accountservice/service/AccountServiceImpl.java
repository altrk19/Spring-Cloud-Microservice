package com.spring.accountservice.service;

import com.spring.accountservice.dto.AccountResource;
import com.spring.accountservice.entity.AccountEntity;
import com.spring.accountservice.repo.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
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
    public AccountResource get(String id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(accountEntity, AccountResource.class);
    }

    @Override
    public Slice<AccountResource> getAll(Pageable pageable) {
        Slice<AccountEntity> accounts = accountRepository.findAll(pageable);
        return null;
    }

    @Override
    @Transactional
    public AccountResource save(AccountResource accountResource) {
        AccountEntity savedAccountEntity = modelMapper.map(accountResource, AccountEntity.class);
        savedAccountEntity = accountRepository.save(savedAccountEntity);
        accountResource.setId(savedAccountEntity.getId());
        return accountResource;
    }

    @Override
    @Transactional
    public AccountResource update(String id, AccountResource accountResource) {
        Assert.isNull(id, "id cannot be null");
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
    public void delete(String id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        accountRepository.delete(accountEntity);
    }

}
