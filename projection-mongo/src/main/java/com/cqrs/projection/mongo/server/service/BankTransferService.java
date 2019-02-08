package com.cqrs.projection.mongo.server.service;

import com.cqrs.projection.mongo.server.model.BankTransfer;
import com.cqrs.projection.mongo.server.repository.BankTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankTransferService {

    @Autowired
    private BankTransferRepository repository;

    @Autowired
    private EventProcessingConfiguration epc;

    public BankTransfer save(BankTransfer bankTransfer) {
        return repository.save(bankTransfer);
    }

    public BankTransfer findById(String id) {
        Optional<BankTransfer> ret = repository.findById(id);

        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    public List<BankTransfer> findAll() {
        return repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void replay() {

        Optional<TrackingEventProcessor> ret =
                epc.eventProcessor("BankTransferMongoListener", TrackingEventProcessor.class);

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
