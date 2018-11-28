package com.cqrs.projection.mongo.server.view;

import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountCreatedEvent;
import com.cqrs.model.events.BankAccountRemovedEvent;
import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.service.BankAccountService;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("BankAccountMongoListener")
public class BankAccountMongoListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountMongoListener.class);

    @Autowired
    private BankAccountService service;

    @EventHandler
    public void on(BankAccountCreatedEvent ev) {
        LOGGER.info("View Handling {} event: {}", ev.getClass().getSimpleName(), ev);

        service.save(BankAccount.Builder
                .create()
                .setId(ev.getId())
                .setBalance(ev.getBalance())
                .setName(ev.getName())
        );

    }

    @EventHandler
    public void on(BankAccountBalanceUpdatedEvent event) {

        LOGGER.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankAccount bankAccount = service.findById(event.getId());

        if (bankAccount != null) {
            bankAccount.setBalance(bankAccount.getBalance().add(event.getBalance()));
            service.save(bankAccount);
        } else {

            LOGGER.warn("Bank Account not found {}", event.getId());
        }

    }

    @EventHandler
    public void on(BankAccountRemovedEvent event) {
        LOGGER.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);

        service.deleteById(event.getId());

    }
}
