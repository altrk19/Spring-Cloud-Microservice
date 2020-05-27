package com.microservice.accountservice.repo;

import com.microservice.accountservice.entity.AccountEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CassandraRepository<AccountEntity, String> {
}
