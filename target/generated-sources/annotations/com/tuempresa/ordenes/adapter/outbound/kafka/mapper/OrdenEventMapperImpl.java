package com.tuempresa.ordenes.adapter.outbound.kafka.mapper;

import com.tuempresa.ordenes.domain.event.OrdenEvent;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T19:03:01-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class OrdenEventMapperImpl implements OrdenEventMapper {

    @Override
    public Object toKafkaEvent(OrdenEvent evento) {
        if ( evento == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }
}
