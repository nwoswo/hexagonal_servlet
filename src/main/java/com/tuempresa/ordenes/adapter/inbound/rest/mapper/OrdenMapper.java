package com.tuempresa.ordenes.adapter.inbound.rest.mapper;

import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.OrdenResponse;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.ItemOrdenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdenMapper {
    
    @Mapping(target = "items", source = "items")
    com.tuempresa.ordenes.application.dto.CrearOrdenRequest toApplicationRequest(CrearOrdenRequest request);
    
    @Mapping(target = "productoId", source = "productoId")
    @Mapping(target = "productoNombre", source = "productoNombre")
    @Mapping(target = "productoDescripcion", source = "productoDescripcion")
    @Mapping(target = "precioUnitario", source = "precioUnitario")
    @Mapping(target = "cantidad", source = "cantidad")
    com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest toApplicationRequest(CrearItemOrdenRequest request);
    
    List<com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest> toApplicationRequestList(List<CrearItemOrdenRequest> items);
    
    // Métodos para convertir de DTOs de aplicación a DTOs de REST
    OrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.OrdenResponse applicationResponse);
    
    ItemOrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.ItemOrdenResponse applicationResponse);
    
    List<OrdenResponse> toRestResponseListOrdenes(List<com.tuempresa.ordenes.application.dto.OrdenResponse> applicationResponses);
    
    List<ItemOrdenResponse> toRestResponseListItemOrdenes(List<com.tuempresa.ordenes.application.dto.ItemOrdenResponse> applicationResponses);
} 