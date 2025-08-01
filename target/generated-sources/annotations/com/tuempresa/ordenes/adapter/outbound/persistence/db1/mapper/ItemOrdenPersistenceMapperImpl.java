package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.ItemOrdenEntity;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T11:49:05-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class ItemOrdenPersistenceMapperImpl implements ItemOrdenPersistenceMapper {

    @Override
    public ItemOrdenEntity toEntity(ItemOrden itemOrden) {
        if ( itemOrden == null ) {
            return null;
        }

        ItemOrdenEntity.ItemOrdenEntityBuilder itemOrdenEntity = ItemOrdenEntity.builder();

        itemOrdenEntity.id( itemOrden.getId() );
        itemOrdenEntity.ordenId( itemOrden.getOrdenId() );
        itemOrdenEntity.productoId( itemOrden.getProductoId() );
        itemOrdenEntity.productoNombre( itemOrden.getProductoNombre() );
        itemOrdenEntity.productoDescripcion( itemOrden.getProductoDescripcion() );
        itemOrdenEntity.precioUnitario( itemOrden.getPrecioUnitario() );
        itemOrdenEntity.cantidad( itemOrden.getCantidad() );
        itemOrdenEntity.subtotal( itemOrden.getSubtotal() );
        itemOrdenEntity.fechaCreacion( itemOrden.getFechaCreacion() );
        itemOrdenEntity.fechaActualizacion( itemOrden.getFechaActualizacion() );

        return itemOrdenEntity.build();
    }

    @Override
    public ItemOrden toDomain(ItemOrdenEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ItemOrden.ItemOrdenBuilder itemOrden = ItemOrden.builder();

        itemOrden.id( entity.getId() );
        itemOrden.ordenId( entity.getOrdenId() );
        itemOrden.productoId( entity.getProductoId() );
        itemOrden.productoNombre( entity.getProductoNombre() );
        itemOrden.productoDescripcion( entity.getProductoDescripcion() );
        itemOrden.precioUnitario( entity.getPrecioUnitario() );
        itemOrden.cantidad( entity.getCantidad() );
        itemOrden.subtotal( entity.getSubtotal() );
        itemOrden.fechaCreacion( entity.getFechaCreacion() );
        itemOrden.fechaActualizacion( entity.getFechaActualizacion() );

        return itemOrden.build();
    }

    @Override
    public List<ItemOrden> toDomainList(List<ItemOrdenEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ItemOrden> list = new ArrayList<ItemOrden>( entities.size() );
        for ( ItemOrdenEntity itemOrdenEntity : entities ) {
            list.add( toDomain( itemOrdenEntity ) );
        }

        return list;
    }
}
