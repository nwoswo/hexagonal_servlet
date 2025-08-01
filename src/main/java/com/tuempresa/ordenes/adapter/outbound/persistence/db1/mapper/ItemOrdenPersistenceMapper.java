package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.ItemOrdenEntity;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemOrdenPersistenceMapper {
    
    ItemOrdenEntity toEntity(ItemOrden itemOrden);
    
    ItemOrden toDomain(ItemOrdenEntity entity);
    
    List<ItemOrden> toDomainList(List<ItemOrdenEntity> entities);
} 