package com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.OrdenEntity;
import com.tuempresa.ordenes.domain.model.Orden;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdenPersistenceMapper {
    
    OrdenEntity toEntity(Orden orden);
    
    Orden toDomain(OrdenEntity entity);
    
    List<Orden> toDomainList(List<OrdenEntity> entities);
} 