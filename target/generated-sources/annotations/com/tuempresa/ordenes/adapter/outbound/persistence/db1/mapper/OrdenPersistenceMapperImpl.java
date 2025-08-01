package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.OrdenEntity;
import com.tuempresa.ordenes.domain.model.Orden;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T11:49:06-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class OrdenPersistenceMapperImpl implements OrdenPersistenceMapper {

    @Override
    public OrdenEntity toEntity(Orden orden) {
        if ( orden == null ) {
            return null;
        }

        OrdenEntity.OrdenEntityBuilder ordenEntity = OrdenEntity.builder();

        ordenEntity.id( orden.getId() );
        ordenEntity.numeroOrden( orden.getNumeroOrden() );
        ordenEntity.clienteId( orden.getClienteId() );
        ordenEntity.clienteNombre( orden.getClienteNombre() );
        ordenEntity.clienteEmail( orden.getClienteEmail() );
        ordenEntity.total( orden.getTotal() );
        ordenEntity.estado( orden.getEstado() );
        ordenEntity.fechaCreacion( orden.getFechaCreacion() );
        ordenEntity.fechaActualizacion( orden.getFechaActualizacion() );

        return ordenEntity.build();
    }

    @Override
    public Orden toDomain(OrdenEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Orden.OrdenBuilder orden = Orden.builder();

        orden.id( entity.getId() );
        orden.numeroOrden( entity.getNumeroOrden() );
        orden.clienteId( entity.getClienteId() );
        orden.clienteNombre( entity.getClienteNombre() );
        orden.clienteEmail( entity.getClienteEmail() );
        orden.total( entity.getTotal() );
        orden.estado( entity.getEstado() );
        orden.fechaCreacion( entity.getFechaCreacion() );
        orden.fechaActualizacion( entity.getFechaActualizacion() );

        return orden.build();
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
