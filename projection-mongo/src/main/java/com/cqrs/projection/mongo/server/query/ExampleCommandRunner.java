package com.cqrs.projection.mongo.server.query;

import com.cqrs.projection.mongo.server.model.BankAccount;
import com.cqrs.projection.mongo.server.query.type.BankAccountQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExampleCommandRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleCommandRunner.class);

    @Autowired
    private QueryGateway queryGateway;

    @Override
    public void run(String... args) throws Exception {


        SubscriptionQueryResult<List<BankAccount>, BankAccount> fetchQueryResult;

        fetchQueryResult = queryGateway.subscriptionQuery(new BankAccountQuery(),
                ResponseTypes.multipleInstancesOf(BankAccount.class),
                ResponseTypes.instanceOf(BankAccount.class));

        fetchQueryResult
                .updates()
                .subscribe(bankAccount -> LOGGER.info("data: {}", bankAccount));

        fetchQueryResult
                .initialResult()
                .flux()
                .subscribe(bankAccounts -> LOGGER.info("data: {}", bankAccounts));


    }
}
