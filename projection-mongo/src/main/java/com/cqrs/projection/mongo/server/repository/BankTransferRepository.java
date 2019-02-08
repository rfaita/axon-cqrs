package com.cqrs.projection.mongo.server.repository;

import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.model.BankTransfer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferRepository extends MongoRepository<BankTransfer, String> {
}
