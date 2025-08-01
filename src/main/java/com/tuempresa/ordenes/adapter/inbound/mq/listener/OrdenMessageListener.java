package com.tuempresa.ordenes.adapter.inbound.mq.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdenMessageListener {
    
    @RabbitListener(queues = "${rabbitmq.queues.orden-created}")
    public void procesarOrdenCreada(String mensaje) {
        log.info("Procesando mensaje de orden creada desde RabbitMQ: {}", mensaje);
        // Aquí se procesaría el mensaje de orden creada
    }
    
    @RabbitListener(queues = "${rabbitmq.queues.orden-cancelled}")
    public void procesarOrdenCancelada(String mensaje) {
        log.info("Procesando mensaje de orden cancelada desde RabbitMQ: {}", mensaje);
        // Aquí se procesaría el mensaje de orden cancelada
    }
    
    @RabbitListener(queues = "${rabbitmq.queues.item-orden-created}")
    public void procesarItemOrdenCreado(String mensaje) {
        log.info("Procesando mensaje de item de orden creado desde RabbitMQ: {}", mensaje);
        // Aquí se procesaría el mensaje de item de orden creado
    }
} 