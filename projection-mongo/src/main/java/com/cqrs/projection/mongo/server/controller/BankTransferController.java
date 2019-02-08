package com.cqrs.projection.mongo.server.controller;


import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.model.BankTransfer;
import com.cqrs.projection.mongo.server.service.BankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank-transfers")
public class BankTransferController {

    @Autowired
    private BankTransferService service;

    @GetMapping("/replay")
    public void replay() {
        service.replay();
    }

    @GetMapping("")
    public List<BankTransfer> findAll() {
        return service.findAll();

    }
}