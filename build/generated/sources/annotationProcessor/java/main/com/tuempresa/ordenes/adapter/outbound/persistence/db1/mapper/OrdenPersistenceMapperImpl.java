package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.OrdenEntity;
import com.tuempresa.ordenes.domain.model.Orden;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T12:16:40-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class OrdenPersistenceMapperImpl implements OrdenPersistenceMapper {

    @Override
    public OrdenEntity toEntity(Orden orden) {
        if ( orden == null ) {
            return null;
        }

        OrdenEntity ordenEntity = new OrdenEntity();

        return ordenEntity;
    }

    @Override
    public Orden toDomain(OrdenEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String clienteId = null;
        String clienteNombre = null;
        String clienteEmail = null;

        Orden orden = new Orden( clienteId, clienteNombre, clienteEmail );

        return orden;
    }

    @Override
    public List<Orden> toDomainList(List<OrdenEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Orden> list = new ArrayList<Orden>( entities.size() );
        for ( OrdenEntity ordenEntity : entities ) {
            list.add( toDomain( ordenEntity ) );
        }

        return list;
    }
}
