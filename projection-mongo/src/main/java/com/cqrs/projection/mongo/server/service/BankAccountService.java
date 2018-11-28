package com.cqrs.projection.mongo.server.service;

import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.repository.BankAccountRepository;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    private BankAccountRepository repository;

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

            LOGGER.info("{}: supportsReset: {}", proc.getName(), proc.supportsReset());
            LOGGER.info("{}: isRunning: {}", proc.getName(), proc.isRunning());
            LOGGER.info("{}: processingStatus: {}", proc.getName(), proc.processingStatus().values());


            proc.shutDown();
            proc.resetTokens();
            proc.start();

        } else {
            throw new ValidationException("Process not found.");
        }


    }
}
