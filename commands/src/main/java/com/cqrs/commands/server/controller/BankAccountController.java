package com.cqrs.commands.server.controller;


import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.cqrs.model.commands.AddMoneyBankAccountCommand;
import com.cqrs.model.commands.CreateBankAccountCommand;
import com.cqrs.model.commands.RemoveBankAccountCommand;
import com.cqrs.model.commands.RemoveMoneyBankAccountCommand;
import com.cqrs.model.dto.BankAccountDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public CreateBankAccountCommand create(@RequestBody BankAccountDTO dto) {
        CreateBankAccountCommand command = new CreateBankAccountCommand(UUID.randomUUID().toString(), dto.getName());
        LOGGER.info("Executing command: {}", command);
        commandGateway.sendAndWait(command);
        return command;
    }

    @PutMapping("/{id}/add")
    public CompletableFuture<AddMoneyBankAccountCommand> addMoney(@PathVariable String id, @RequestBody BankAccountDTO dto) {
        AddMoneyBankAccountCommand command = new AddMoneyBankAccountCommand(id, UUID.randomUUID().toString(), dto.getBalance());
        LOGGER.info("Executing command: {}", command);
        return commandGateway.send(command);

    }

    @DeleteMapping("/{id}/remove")
    public CompletableFuture<RemoveMoneyBankAccountCommand> removeMoney(@PathVariable String id, @RequestBody BankAccountDTO dto) {
        RemoveMoneyBankAccountCommand command = new RemoveMoneyBankAccountCommand(id, dto.getBalance());
        LOGGER.info("Executing command: {}", command);
        return commandGateway.send(command);

    }

    @DeleteMapping("/{id}")
    public CompletableFuture<RemoveBankAccountCommand> remove(@PathVariable String id) {
        RemoveBankAccountCommand command = new RemoveBankAccountCommand(id);
        LOGGER.info("Executing command: {}", command);
        return commandGateway.send(command);
    }
}