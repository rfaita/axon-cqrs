package com.cqrs.projection.mongo.server.service;

import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.repository.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private EventProcessingConfiguration epc;

    public BankAccount save(BankAccount bankAccount) {
        return repository.save(bankAccount);
    }

    public BankAccount findById(String id) {
        Optional<BankAccount> ret = repository.findById(id);

        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    public List<BankAccount> findAll() {
        return repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void replay() {

        Optional<TrackingEventProcessor> ret =
                epc.eventProcessor("BankAccountMongoListener", TrackingEventProcessor.class);

        if (ret.isPresent()) {

            repository.deleteAll();

            TrackingEventProcessor proc = ret.get();

            log.info("{}: supportsReset: {}", proc.getName(), proc.supportsReset());
            log.info("{}: isRunning: {}", proc.getName(), proc.isRunning());
            log.info("{}: processingStatus: {}", proc.getName(), proc.processingStatus().values());


            proc.shutDown();
            proc.resetTokens();
            proc.start();

        } else {
            throw new ValidationException("Process not found.");
        }


    }
}
