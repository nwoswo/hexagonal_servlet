package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.ItemOrdenEntity;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T12:01:30-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class ItemOrdenPersistenceMapperImpl implements ItemOrdenPersistenceMapper {

    @Override
    public ItemOrdenEntity toEntity(ItemOrden itemOrden) {
        if ( itemOrden == null ) {
            return null;
        }

        ItemOrdenEntity itemOrdenEntity = new ItemOrdenEntity();

        return itemOrdenEntity;
    }

    @Override
    public ItemOrden toDomain(ItemOrdenEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID ordenId = null;
        String productoId = null;
        String productoNombre = null;
        String productoDescripcion = null;
        BigDecimal precioUnitario = null;
        Integer cantidad = null;

        ItemOrden itemOrden = new ItemOrden( ordenId, productoId, productoNombre, productoDescripcion, precioUnitario, cantidad );

        return itemOrden;
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
