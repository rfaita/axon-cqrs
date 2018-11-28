package com.cqrs.commands.config;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Bean
    @Primary
    public Serializer serializer() {

        return new JacksonSerializer.Builder().build();
    }

    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {

        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public SnapshotTriggerDefinition eventCountSnapshot(Snapshotter snapshotter) {

        return new EventCountSnapshotTriggerDefinition(snapshotter, 50);
    }

}
