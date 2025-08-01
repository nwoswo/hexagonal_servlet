package com.tuempresa.ordenes.adapter.outbound.kafka.producer;

import com.tuempresa.ordenes.adapter.outbound.kafka.mapper.OrdenEventMapper;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.domain.event.OrdenEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdenEventProducer implements EventPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrdenEventMapper ordenEventMapper;
    
    @Value("${kafka.topics.orden-created}")
    private String ordenCreatedTopic;
    
    @Value("${kafka.topics.orden-updated}")
    private String ordenUpdatedTopic;
    
    @Value("${kafka.topics.orden-cancelled}")
    private String ordenCancelledTopic;
    
    @Value("${kafka.topics.item-orden-created}")
    private String itemOrdenCreatedTopic;
    
    @Value("${kafka.topics.item-orden-updated}")
    private String itemOrdenUpdatedTopic;
    
    @Value("${kafka.topics.item-orden-deleted}")
    private String itemOrdenDeletedTopic;
    
    @Override
    public void publicarEvento(OrdenEvent evento) {
        try {
            String topic = determinarTopic(evento);
            String key = generarKey(evento);
            Object payload = ordenEventMapper.toKafkaEvent(evento);
            
            kafkaTemplate.send(topic, key, payload);
            log.info("Evento publicado exitosamente en topic: {}, key: {}", topic, key);
            
        } catch (Exception e) {
            log.error("Error al publicar evento en Kafka: {}", e.getMessage(), e);
            throw new RuntimeException("Error al publicar evento en Kafka", e);
        }
    }
    
    private String determinarTopic(OrdenEvent evento) {
        return switch (evento.getEventType()) {
            case "ORDEN_CREADA" -> ordenCreatedTopic;
            case "ORDEN_CANCELADA" -> ordenCancelledTopic;
            case "ITEM_ORDEN_CREADO" -> itemOrdenCreatedTopic;
            case "ITEM_ORDEN_ACTUALIZADO" -> itemOrdenUpdatedTopic;
            case "ITEM_ORDEN_ELIMINADO" -> itemOrdenDeletedTopic;
            default -> ordenUpdatedTopic;
        };
    }
    
    private String generarKey(OrdenEvent evento) {
        return evento.getEventId().toString();
    }
} 