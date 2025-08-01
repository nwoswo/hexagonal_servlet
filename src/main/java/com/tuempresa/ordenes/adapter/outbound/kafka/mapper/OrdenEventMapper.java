package com.tuempresa.ordenes.adapter.outbound.kafka.mapper;

import com.tuempresa.ordenes.domain.event.OrdenEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdenEventMapper {
    
    Object toKafkaEvent(OrdenEvent evento);
} 