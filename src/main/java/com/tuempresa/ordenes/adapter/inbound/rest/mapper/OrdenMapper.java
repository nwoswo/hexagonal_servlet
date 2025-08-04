package com.tuempresa.ordenes.adapter.inbound.rest.mapper;

import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.OrdenResponse;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.ItemOrdenResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdenMapper {
    
    com.tuempresa.ordenes.application.dto.CrearOrdenCommand toApplicationRequest(CrearOrdenRequest request);
    
    com.tuempresa.ordenes.application.dto.CrearItemOrdenCommand toApplicationRequest(CrearItemOrdenRequest request);
    
    List<com.tuempresa.ordenes.application.dto.CrearItemOrdenCommand> toApplicationRequestList(List<CrearItemOrdenRequest> items);
    
    // Métodos para convertir de DTOs de aplicación a DTOs de REST
    OrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.OrdenData applicationResponse);
    
    ItemOrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.ItemOrdenData applicationResponse);
    
    List<OrdenResponse> toRestResponseListOrdenes(List<com.tuempresa.ordenes.application.dto.OrdenData> applicationResponses);
    
    List<ItemOrdenResponse> toRestResponseListItemOrdenes(List<com.tuempresa.ordenes.application.dto.ItemOrdenData> applicationResponses);
} 