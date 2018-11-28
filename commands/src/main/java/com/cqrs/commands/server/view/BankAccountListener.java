package com.cqrs.commands.server.view;

import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountCreatedEvent;
import com.cqrs.model.events.BankAccountRemovedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("BankAccountListener")
@AllowReplay
public class BankAccountListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountListener.class);


    @EventHandler
    public void on(BankAccountCreatedEvent event) {
        LOGGER.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventHandler
    public void on(BankAccountBalanceUpdatedEvent event) {
        LOGGER.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventHandler
    public void on(BankAccountRemovedEvent event) {
        LOGGER.info("View Handling {} event: {}", event.getClass().getSimpleName(), event);
    }
}
