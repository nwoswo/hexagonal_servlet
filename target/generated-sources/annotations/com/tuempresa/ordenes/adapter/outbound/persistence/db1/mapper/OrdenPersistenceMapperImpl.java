package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.OrdenEntity;
import com.tuempresa.ordenes.domain.model.Orden;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T12:56:28-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrdenPersistenceMapperImpl implements OrdenPersistenceMapper {

    @Override
    public OrdenEntity toEntity(Orden orden) {
        if ( orden == null ) {
            return null;
        }

        OrdenEntity.OrdenEntityBuilder ordenEntity = OrdenEntity.builder();

        ordenEntity.clienteEmail( orden.getClienteEmail() );
        ordenEntity.clienteId( orden.getClienteId() );
        ordenEntity.clienteNombre( orden.getClienteNombre() );
        ordenEntity.estado( orden.getEstado() );
        ordenEntity.fechaActualizacion( orden.getFechaActualizacion() );
        ordenEntity.fechaCreacion( orden.getFechaCreacion() );
        ordenEntity.id( orden.getId() );
        ordenEntity.numeroOrden( orden.getNumeroOrden() );
        ordenEntity.total( orden.getTotal() );

        return ordenEntity.build();
    }

    @Override
    public Orden toDomain(OrdenEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Orden.OrdenBuilder orden = Orden.builder();

        orden.clienteEmail( entity.getClienteEmail() );
        orden.clienteId( entity.getClienteId() );
        orden.clienteNombre( entity.getClienteNombre() );
        orden.estado( entity.getEstado() );
        orden.fechaActualizacion( entity.getFechaActualizacion() );
        orden.fechaCreacion( entity.getFechaCreacion() );
        orden.id( entity.getId() );
        orden.numeroOrden( entity.getNumeroOrden() );
        orden.total( entity.getTotal() );

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
