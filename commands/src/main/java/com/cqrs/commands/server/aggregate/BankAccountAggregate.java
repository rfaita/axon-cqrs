package com.cqrs.commands.server.aggregate;

import com.cqrs.model.commands.AddMoneyBankAccountCommand;
import com.cqrs.model.commands.CreateBankAccountCommand;
import com.cqrs.model.commands.RemoveBankAccountCommand;
import com.cqrs.model.commands.WithdrawnMoneyBankAccountCommand;
import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountCreatedEvent;
import com.cqrs.model.events.BankAccountRemovedEvent;
import com.cqrs.model.events.BankAccountWithdrawnRejectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate(snapshotTriggerDefinition = "eventCountSnapshot")
public class BankAccountAggregate {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountAggregate.class);

    @AggregateIdentifier
    private String id;
    private String name;
    private BigDecimal balance;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BankAccountAggregate() {
        LOGGER.info("empty constructor invoked");
    }

    @CommandHandler
    public BankAccountAggregate(CreateBankAccountCommand cmd) {
        LOGGER.info("Handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        Assert.hasLength(cmd.getId(), "Id should not be empty or null.");
        Assert.hasLength(cmd.getName(), "Name should not be empty or null.");

        apply(new BankAccountCreatedEvent(cmd.getId(), cmd.getName(), BigDecimal.ZERO));
        LOGGER.info("Done handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
    }

    @CommandHandler
    public AddMoneyBankAccountCommand handle(AddMoneyBankAccountCommand cmd) {
        LOGGER.info("Handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        Assert.hasLength(cmd.getId(), "Bank Id should not be empty or null.");
        Assert.notNull(cmd.getValue(), "Balance should not be empty or null.");
        Assert.isTrue(cmd.getValue().doubleValue() > 0d, "Value must be greater than zero.");
        apply(new BankAccountBalanceUpdatedEvent(cmd.getId(), cmd.getTransactionId(), cmd.getValue()));
        LOGGER.info("Done handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        return cmd;
    }

    @CommandHandler
    public WithdrawnMoneyBankAccountCommand handle(WithdrawnMoneyBankAccountCommand cmd) {
        LOGGER.info("Handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        Assert.hasLength(cmd.getId(), "Bank Id should not be empty or null.");
        Assert.notNull(cmd.getValue(), "Balance should not be empty or null.");
        Assert.isTrue(cmd.getValue().doubleValue() < 0d, "Value must be lower than zero.");

        if (canWithdraw(cmd.getValue())) {
            apply(new BankAccountBalanceUpdatedEvent(cmd.getId(), cmd.getTransactionId(), cmd.getValue()));
        } else {
            apply(new BankAccountWithdrawnRejectedEvent(cmd.getId(), cmd.getTransactionId()));
        }

        LOGGER.info("Done handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        return cmd;
    }

    @CommandHandler
    public RemoveBankAccountCommand handle(RemoveBankAccountCommand cmd) {
        LOGGER.info("Handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        Assert.hasLength(cmd.getId(), "Id should not be empty or null.");
        apply(new BankAccountRemovedEvent(cmd.getId()));
        LOGGER.info("Done handling {} command: {}", cmd.getClass().getSimpleName(), cmd);
        return cmd;
    }

    @EventSourcingHandler
    public void on(BankAccountCreatedEvent event) {
        LOGGER.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.id = event.getId();
        this.name = event.getName();
        this.balance = event.getBalance();
        LOGGER.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(BankAccountBalanceUpdatedEvent event) {
        LOGGER.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.balance = this.balance.add(event.getBalance());
        LOGGER.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(BankAccountRemovedEvent event) {
        markDeleted();
        LOGGER.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    private Boolean canWithdraw(BigDecimal amount) {
        return this.balance.compareTo(amount) == 1;
    }
}