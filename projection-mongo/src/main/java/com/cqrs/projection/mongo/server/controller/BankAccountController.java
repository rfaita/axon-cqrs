package com.cqrs.projection.mongo.server.controller;


import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/replay")
    public void replay() {
        bankAccountService.replay();
    }

    @GetMapping("")
    public List<BankAccount> findAll() {
        return bankAccountService.findAll();

    }
}