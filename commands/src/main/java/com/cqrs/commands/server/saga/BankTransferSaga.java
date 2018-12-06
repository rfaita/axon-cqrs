package com.cqrs.commands.server.saga;

import com.cqrs.model.commands.AddMoneyBankAccountCommand;
import com.cqrs.model.commands.WithdrawnMoneyBankAccountCommand;
import com.cqrs.model.commands.transfer.CompleteTransferCommand;
import com.cqrs.model.commands.transfer.FailTransferCommand;
import com.cqrs.model.events.BankAccountBalanceUpdatedEvent;
import com.cqrs.model.events.BankAccountWithdrawnRejectedEvent;
import com.cqrs.model.events.transfer.TransferRequestedEvent;
import lombok.Builder;
import lombok.Data;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Saga
@Data
@Builder
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
                WithdrawnMoneyBankAccountCommand.builder()
                        .id(this.sourceId)
                        .transactionId(this.transactionId)
                        .value(this.amount.multiply(new BigDecimal(-1)))
                        .build()
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = BANK_TRANSFER_ASSOCIATION_PROPERTY)
    public void on(BankAccountWithdrawnRejectedEvent event) {
        commandGateway.send(
                FailTransferCommand.builder()
                        .transactionId(this.transactionId)
                        .build()
        );
    }

    @SagaEventHandler(associationProperty = BANK_TRANSFER_ASSOCIATION_PROPERTY)
    public void on(BankAccountBalanceUpdatedEvent event) {

        //verify if the event come from sourceId
        if (event.getId().equals(this.sourceId)) {
            this.withdrawnDone = true;
            commandGateway.send(
                    AddMoneyBankAccountCommand.builder()
                            .id(this.destinationId)
                            .transactionId(this.transactionId)
                            .value(this.amount)
                            .build()
            );
        } else {
            commandGateway.send(
                    CompleteTransferCommand.builder()
                            .transactionId(this.transactionId)
                            .build()
            );
            SagaLifecycle.end();
        }
    }

}