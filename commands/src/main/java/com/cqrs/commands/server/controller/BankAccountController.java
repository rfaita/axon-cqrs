package com.cqrs.commands.server.controller;


import com.cqrs.model.commands.AddMoneyBankAccountCommand;
import com.cqrs.model.commands.CreateBankAccountCommand;
import com.cqrs.model.commands.RemoveBankAccountCommand;
import com.cqrs.model.commands.WithdrawnMoneyBankAccountCommand;
import com.cqrs.model.commands.transfer.RequestTransferCommand;
import com.cqrs.model.dto.BalanceBankAccountDTO;
import com.cqrs.model.dto.BankAccountDTO;
import com.cqrs.model.dto.BankTransferDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public CreateBankAccountCommand create(@RequestBody BankAccountDTO dto) {
        CreateBankAccountCommand command =
                CreateBankAccountCommand
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .name(dto.getName())
                        .build();
        LOGGER.info("Executing command: {}", command);
        commandGateway.sendAndWait(command);
        return command;
    }

    @PutMapping("/{id}/add")
    public CompletableFuture<AddMoneyBankAccountCommand> addMoney(@PathVariable String id, @RequestBody BalanceBankAccountDTO dto) {
        AddMoneyBankAccountCommand command =
                AddMoneyBankAccountCommand
                        .builder()
                        .id(id)
                        .transactionId(UUID.randomUUID().toString())
                        .value(dto.getBalance())
                        .build();
        LOGGER.info("Executing command: {}", command);
        return commandGateway.send(command);

    }

    @DeleteMapping("/{id}/remove")
    public CompletableFuture<WithdrawnMoneyBankAccountCommand> removeMoney(@PathVariable String id, @RequestBody BalanceBankAccountDTO dto) {
        WithdrawnMoneyBankAccountCommand command =
                WithdrawnMoneyBankAccountCommand
                        .builder()
                        .id(id)
                        .transactionId(UUID.randomUUID().toString())
                        .value(dto.getBalance())
                        .build();
        LOGGER.info("Executing command: {}", command);
        return commandGateway.send(command);

    }

    @DeleteMapping("/{id}")
    public CompletableFuture<RemoveBankAccountCommand> remove(@PathVariable String id) {
        RemoveBankAccountCommand command =
                RemoveBankAccountCommand.builder().id(id).build();
        LOGGER.info("Executing command: {}", command);
        return commandGateway.send(command);
    }

    @PostMapping("/transfer")
    public RequestTransferCommand transfer(@RequestBody BankTransferDTO dto) {
        RequestTransferCommand command
                = RequestTransferCommand
                .builder()
                .transactionId(UUID.randomUUID().toString())
                .sourceId(dto.getSourceId())
                .destinationId(dto.getDestinationId())
                .amount(dto.getAmount())
                .build();
        LOGGER.info("Executing command: {}", command);
        commandGateway.send(command);

        return command;
    }
}