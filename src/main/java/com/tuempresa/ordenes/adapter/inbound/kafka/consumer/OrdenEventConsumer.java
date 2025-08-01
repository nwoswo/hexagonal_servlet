package com.tuempresa.ordenes.adapter.inbound.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdenEventConsumer {
    
    @KafkaListener(topics = "${kafka.topics.orden-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumirOrdenCreada(String mensaje) {
        log.info("Consumiendo evento de orden creada: {}", mensaje);
        // Aquí se procesaría el evento de orden creada
    }
    
    @KafkaListener(topics = "${kafka.topics.orden-cancelled}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumirOrdenCancelada(String mensaje) {
        log.info("Consumiendo evento de orden cancelada: {}", mensaje);
        // Aquí se procesaría el evento de orden cancelada
    }
    
    @KafkaListener(topics = "${kafka.topics.item-orden-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumirItemOrdenCreado(String mensaje) {
        log.info("Consumiendo evento de item de orden creado: {}", mensaje);
        // Aquí se procesaría el evento de item de orden creado
    }
} 