package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.ItemOrdenEntity;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T12:55:50-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ItemOrdenPersistenceMapperImpl implements ItemOrdenPersistenceMapper {

    @Override
    public ItemOrdenEntity toEntity(ItemOrden itemOrden) {
        if ( itemOrden == null ) {
            return null;
        }

        ItemOrdenEntity.ItemOrdenEntityBuilder itemOrdenEntity = ItemOrdenEntity.builder();

        itemOrdenEntity.cantidad( itemOrden.getCantidad() );
        itemOrdenEntity.fechaActualizacion( itemOrden.getFechaActualizacion() );
        itemOrdenEntity.fechaCreacion( itemOrden.getFechaCreacion() );
        itemOrdenEntity.id( itemOrden.getId() );
        itemOrdenEntity.ordenId( itemOrden.getOrdenId() );
        itemOrdenEntity.precioUnitario( itemOrden.getPrecioUnitario() );
        itemOrdenEntity.productoDescripcion( itemOrden.getProductoDescripcion() );
        itemOrdenEntity.productoId( itemOrden.getProductoId() );
        itemOrdenEntity.productoNombre( itemOrden.getProductoNombre() );
        itemOrdenEntity.subtotal( itemOrden.getSubtotal() );

        return itemOrdenEntity.build();
    }

    @Override
    public ItemOrden toDomain(ItemOrdenEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ItemOrden.ItemOrdenBuilder itemOrden = ItemOrden.builder();

        itemOrden.cantidad( entity.getCantidad() );
        itemOrden.fechaActualizacion( entity.getFechaActualizacion() );
        itemOrden.fechaCreacion( entity.getFechaCreacion() );
        itemOrden.id( entity.getId() );
        itemOrden.ordenId( entity.getOrdenId() );
        itemOrden.precioUnitario( entity.getPrecioUnitario() );
        itemOrden.productoDescripcion( entity.getProductoDescripcion() );
        itemOrden.productoId( entity.getProductoId() );
        itemOrden.productoNombre( entity.getProductoNombre() );
        itemOrden.subtotal( entity.getSubtotal() );

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
