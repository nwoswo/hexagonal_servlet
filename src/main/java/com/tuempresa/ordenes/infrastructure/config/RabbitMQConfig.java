package com.tuempresa.ordenes.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queues.orden-created}")
    private String ordenCreatedQueue;

    @Value("${rabbitmq.queues.orden-cancelled}")
    private String ordenCancelledQueue;

    @Value("${rabbitmq.queues.item-orden-created}")
    private String itemOrdenCreatedQueue;

    @Bean
    public Queue ordenCreatedQueue() {
        return QueueBuilder.durable(ordenCreatedQueue).build();
    }

    @Bean
    public Queue ordenCancelledQueue() {
        return QueueBuilder.durable(ordenCancelledQueue).build();
    }

    @Bean
    public Queue itemOrdenCreatedQueue() {
        return QueueBuilder.durable(itemOrdenCreatedQueue).build();
    }
} 