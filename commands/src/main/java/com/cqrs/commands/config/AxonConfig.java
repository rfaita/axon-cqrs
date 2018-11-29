package com.cqrs.commands.config;

import com.mongodb.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {

        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public SnapshotTriggerDefinition eventCountSnapshot(Snapshotter snapshotter) {

        return new EventCountSnapshotTriggerDefinition(snapshotter, 50);
    }

    @Bean
    public TokenStore tokenStore(MongoClient mongoClient) {
        return MongoTokenStore.builder()
                .serializer(JacksonSerializer.builder().build())
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(mongoClient).build())
                .build();
    }

    @Bean
    public SagaStore sagaStore(MongoClient mongoClient) {
        return MongoSagaStore.builder()
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(mongoClient).build())
                .build();
    }

}
