package com.cqrs.projection.mongo.server.projection;

import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountCreatedEvent;
import com.cqrs.model.events.BankAccountRemovedEvent;
import com.cqrs.model.events.transfer.TransferCompletedEvent;
import com.cqrs.model.events.transfer.TransferFailedEvent;
import com.cqrs.model.events.transfer.TransferRequestedEvent;
import com.cqrs.projection.mongo.server.enums.BankTransferStatus;
import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.model.BankTransfer;
import com.cqrs.projection.mongo.server.service.BankAccountService;
import com.cqrs.projection.mongo.server.service.BankTransferService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ProcessingGroup("BankTransferMongoListener")
public class BankTransferMongoListener {

    @Autowired
    private BankTransferService service;

    @EventHandler
    public void on(TransferRequestedEvent ev) {
        log.info("View Handling {} event: {}", ev.getClass().getSimpleName(), ev);


        service.save(BankTransfer.builder()
                .transactionId(ev.getTransactionId())
                .amount(ev.getAmount())
                .sourceId(ev.getSourceId())
                .destinationId(ev.getDestinationId())
                .status(BankTransferStatus.STARTED)
                .build()
        );

    }

    @EventHandler
    public void on(TransferFailedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankTransfer bankTransfer = service.findById(event.getTransactionId());

        if (bankTransfer != null) {
            bankTransfer.setStatus(BankTransferStatus.FAILED);
            service.save(bankTransfer);
        } else {

            log.warn("Bank Transfer not found {}", event.getTransactionId());
        }


    }

    @EventHandler
    public void on(TransferCompletedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankTransfer bankTransfer = service.findById(event.getTransactionId());

        if (bankTransfer != null) {
            bankTransfer.setStatus(BankTransferStatus.COMPLETED);
            service.save(bankTransfer);
        } else {

            log.warn("Bank Transfer not found {}", event.getTransactionId());
        }


    }

    @EventHandler
    public void on(BankAccountRemovedEvent event) {
        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);

        service.deleteById(event.getId());

    }
}
