package com.cqrs.projection.mongo.server.query;

import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountCreatedEvent;
import com.cqrs.model.events.BankAccountRemovedEvent;
import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.query.type.BankAccountQuery;
import com.cqrs.projection.mongo.server.service.BankAccountService;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ProcessingGroup("BankAccountQueryListener")
public class BankAccountQueryListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountQueryListener.class);

    @Autowired
    private BankAccountService service;

    @Autowired
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(BankAccountCreatedEvent event) {
        updateQuery(event.getId());
    }

    @EventHandler
    public void on(BankAccountBalanceUpdatedEvent event) {
        updateQuery(event.getId());
    }

    @EventHandler
    public void on(BankAccountRemovedEvent event) {
        updateQuery(event.getId());
    }

    private void updateQuery(String id) {
        queryUpdateEmitter.emit(BankAccountQuery.class, teste -> true, service.findById(id));
    }

    @QueryHandler
    public List<BankAccount> queryTest(BankAccountQuery teste) {
        return service.findAll();
    }
}
