package com.cqrs.projection.mongo.server.projection;

import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountCreatedEvent;
import com.cqrs.model.events.BankAccountRemovedEvent;
import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
@ProcessingGroup("BankAccountMongoListener")
public class BankAccountMongoListener {

    @Autowired
    private BankAccountService service;

    @EventHandler
    public void on(BankAccountCreatedEvent ev) {
        log.info("View Handling {} event: {}", ev.getClass().getSimpleName(), ev);


        service.save(BankAccount.builder()
                .id(ev.getId())
                .balance(ev.getBalance())
                .name(ev.getName())
                .build()
        );

    }

    @EventHandler
    public void on(BankAccountBalanceUpdatedEvent event) {

        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
        BankAccount bankAccount = service.findById(event.getId());

        if (bankAccount != null) {
            bankAccount.setBalance(bankAccount.getBalance().add(event.getBalance()));
            service.save(bankAccount);
        } else {

            log.warn("Bank Account not found {}", event.getId());
        }


    }

    @EventHandler
    public void on(BankAccountRemovedEvent event) {
        log.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);

        service.deleteById(event.getId());

    }
}
