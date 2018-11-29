package com.cqrs.commands.server.saga;

import com.cqrs.model.commands.AddMoneyBankAccountCommand;
import com.cqrs.model.commands.WithdrawnMoneyBankAccountCommand;
import com.cqrs.model.commands.transfer.CompleteTransferCommand;
import com.cqrs.model.commands.transfer.FailTransferCommand;
import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountWithdrawnRejectedEvent;
import com.cqrs.model.events.transfer.TransferRequestedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Saga
public class BankTransferSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private static final String BANK_TRANSFER_ASSOCIATION_PROPERTY = "transactionId";

    private String transactionId;
    private String sourceId;
    private String destinationId;
    private BigDecimal amount;
    private Boolean withdrawnDone;

    @StartSaga
    @SagaEventHandler(associationProperty = BANK_TRANSFER_ASSOCIATION_PROPERTY)
    public void on(TransferRequestedEvent event) {

        this.transactionId = event.getTransactionId();
        this.sourceId = event.getSourceId();
        this.destinationId = event.getDestinationId();
        this.amount = event.getAmount();
        this.withdrawnDone = false;

        commandGateway.send(
                new WithdrawnMoneyBankAccountCommand(this.sourceId, this.transactionId, this.amount.multiply(new BigDecimal(-1)))
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = BANK_TRANSFER_ASSOCIATION_PROPERTY)
    public void on(BankAccountWithdrawnRejectedEvent event) {
        commandGateway.send(new FailTransferCommand(this.transactionId));
    }

    @SagaEventHandler(associationProperty = BANK_TRANSFER_ASSOCIATION_PROPERTY)
    public void on(BankAccountBalanceUpdatedEvent event) {

        //verify if the event come from sourceId
        if (event.getId().equals(this.sourceId)) {
            this.withdrawnDone = true;
            commandGateway.send(new AddMoneyBankAccountCommand(this.destinationId, this.transactionId, this.amount));
        } else {
            commandGateway.send(new CompleteTransferCommand(this.transactionId));
            SagaLifecycle.end();
        }
    }

}