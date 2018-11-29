package com.cqrs.commands.server.aggregate;

import com.cqrs.commands.server.enums.BankTransferStatus;
import com.cqrs.model.commands.transfer.CompleteTransferCommand;
import com.cqrs.model.commands.transfer.FailTransferCommand;
import com.cqrs.model.commands.transfer.RequestTransferCommand;
import com.cqrs.model.events.transfer.TransferCompletedEvent;
import com.cqrs.model.events.transfer.TransferFailedEvent;
import com.cqrs.model.events.transfer.TransferRequestedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "eventCountSnapshot")
public class BankTransferAggregate {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountAggregate.class);

    @AggregateIdentifier
    private String transactionId;
    private String sourceId;
    private String destinationId;
    private BigDecimal amount;
    private BankTransferStatus status;

    public String getTransactionId() {
        return transactionId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BankTransferStatus getStatus() {
        return status;
    }

    public BankTransferAggregate() {
        LOGGER.info("empty constructor invoked");
    }


    @CommandHandler
    public BankTransferAggregate(RequestTransferCommand cmd) {
        Assert.hasLength(cmd.getTransactionId(), "Transaction Id should not be empty or null.");
        Assert.hasLength(cmd.getSourceId(), "Source Id should not be empty or null.");
        Assert.hasLength(cmd.getDestinationId(), "Destination Id should not be empty or null.");
        Assert.isTrue(cmd.getAmount().doubleValue() > 0d, "Amount must be greater than zero.");

        apply(new TransferRequestedEvent(cmd.getTransactionId(),
                cmd.getSourceId(),
                cmd.getDestinationId(),
                cmd.getAmount()));
    }

    @CommandHandler
    public  void handle(CompleteTransferCommand cmd) {
        Assert.hasLength(cmd.getTransactionId(), "Transaction Id should not be empty or null.");
        apply(new TransferCompletedEvent(cmd.getTransactionId()));
    }

    @CommandHandler
    public void handle(FailTransferCommand cmd) {
        Assert.hasLength(cmd.getTransactionId(), "Transaction Id should not be empty or null.");
        apply(new TransferFailedEvent(cmd.getTransactionId()));
    }

    @EventSourcingHandler
    public void on(TransferRequestedEvent event) {
        LOGGER.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.transactionId = event.getTransactionId();
        this.destinationId = event.getDestinationId();
        this.sourceId = event.getSourceId();
        this.amount = event.getAmount();
        this.status = BankTransferStatus.STARTED;
        LOGGER.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(TransferCompletedEvent event) {
        LOGGER.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.status = BankTransferStatus.COMPLETED;
        LOGGER.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

    @EventSourcingHandler
    public void on(FailTransferCommand event) {
        LOGGER.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.status = BankTransferStatus.FAILED;
        LOGGER.info("Done handling {} event: {}", event.getClass().getSimpleName(), event);
    }

}
