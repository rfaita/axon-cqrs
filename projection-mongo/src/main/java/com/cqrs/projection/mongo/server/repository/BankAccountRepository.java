package com.cqrs.projection.mongo.server.repository;

import com.cqrs.projection.mongo.server.model.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
}
