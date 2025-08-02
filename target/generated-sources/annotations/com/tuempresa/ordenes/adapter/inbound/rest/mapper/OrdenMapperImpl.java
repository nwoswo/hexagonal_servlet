package com.tuempresa.ordenes.adapter.inbound.rest.mapper;

import com.tuempresa.ordenes.adapter.inbound.rest.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.OrdenResponse;
import com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.application.dto.CrearOrdenRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-01T22:04:43-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrdenMapperImpl implements OrdenMapper {

    @Override
    public CrearOrdenRequest toApplicationRequest(com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearOrdenRequest request) {
        if ( request == null ) {
            return null;
        }

        String clienteId = null;
        String clienteNombre = null;
        String clienteEmail = null;
        List<CrearItemOrdenRequest> items = null;

        clienteId = request.clienteId();
        clienteNombre = request.clienteNombre();
        clienteEmail = request.clienteEmail();
        items = toApplicationRequestList( request.items() );

        CrearOrdenRequest crearOrdenRequest = new CrearOrdenRequest( clienteId, clienteNombre, clienteEmail, items );

        return crearOrdenRequest;
    }

    @Override
    public CrearItemOrdenRequest toApplicationRequest(com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest request) {
        if ( request == null ) {
            return null;
        }

        String productoId = null;
        String productoNombre = null;
        String productoDescripcion = null;
        Double precioUnitario = null;
        Integer cantidad = null;

        productoId = request.productoId();
        productoNombre = request.productoNombre();
        productoDescripcion = request.productoDescripcion();
        precioUnitario = request.precioUnitario();
        cantidad = request.cantidad();

        CrearItemOrdenRequest crearItemOrdenRequest = new CrearItemOrdenRequest( productoId, productoNombre, productoDescripcion, precioUnitario, cantidad );

        return crearItemOrdenRequest;
    }

    @Override
    public List<CrearItemOrdenRequest> toApplicationRequestList(List<com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest> items) {
        if ( items == null ) {
            return null;
        }

        List<CrearItemOrdenRequest> list = new ArrayList<CrearItemOrdenRequest>( items.size() );
        for ( com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest crearItemOrdenRequest : items ) {
            list.add( toApplicationRequest( crearItemOrdenRequest ) );
        }

        return list;
    }

    @Override
    public OrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.OrdenResponse applicationResponse) {
        if ( applicationResponse == null ) {
            return null;
        }

        UUID id = null;
        String numeroOrden = null;
        String clienteId = null;
        String clienteNombre = null;
        String clienteEmail = null;
        BigDecimal total = null;
        String estado = null;
        LocalDateTime fechaCreacion = null;
        LocalDateTime fechaActualizacion = null;
        List<ItemOrdenResponse> items = null;

        id = applicationResponse.id();
        numeroOrden = applicationResponse.numeroOrden();
        clienteId = applicationResponse.clienteId();
        clienteNombre = applicationResponse.clienteNombre();
        clienteEmail = applicationResponse.clienteEmail();
        total = applicationResponse.total();
        estado = applicationResponse.estado();
        fechaCreacion = applicationResponse.fechaCreacion();
        fechaActualizacion = applicationResponse.fechaActualizacion();
        items = toRestResponseListItemOrdenes( applicationResponse.items() );

        OrdenResponse ordenResponse = new OrdenResponse( id, numeroOrden, clienteId, clienteNombre, clienteEmail, total, estado, fechaCreacion, fechaActualizacion, items );

        return ordenResponse;
    }

    @Override
    public ItemOrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.ItemOrdenResponse applicationResponse) {
        if ( applicationResponse == null ) {
            return null;
        }

        UUID id = null;
        String productoId = null;
        String productoNombre = null;
        String productoDescripcion = null;
        BigDecimal precioUnitario = null;
        Integer cantidad = null;
        BigDecimal subtotal = null;
        LocalDateTime fechaCreacion = null;
        LocalDateTime fechaActualizacion = null;

        id = applicationResponse.id();
        productoId = applicationResponse.productoId();
        productoNombre = applicationResponse.productoNombre();
        productoDescripcion = applicationResponse.productoDescripcion();
        precioUnitario = applicationResponse.precioUnitario();
        cantidad = applicationResponse.cantidad();
        subtotal = applicationResponse.subtotal();
        fechaCreacion = applicationResponse.fechaCreacion();
        fechaActualizacion = applicationResponse.fechaActualizacion();

        ItemOrdenResponse itemOrdenResponse = new ItemOrdenResponse( id, productoId, productoNombre, productoDescripcion, precioUnitario, cantidad, subtotal, fechaCreacion, fechaActualizacion );

        return itemOrdenResponse;
    }

    @Override
    public List<OrdenResponse> toRestResponseListOrdenes(List<com.tuempresa.ordenes.application.dto.OrdenResponse> applicationResponses) {
        if ( applicationResponses == null ) {
            return null;
        }

        List<OrdenResponse> list = new ArrayList<OrdenResponse>( applicationResponses.size() );
        for ( com.tuempresa.ordenes.application.dto.OrdenResponse ordenResponse : applicationResponses ) {
            list.add( toRestResponse( ordenResponse ) );
        }

        return list;
    }

    @Override
    public List<ItemOrdenResponse> toRestResponseListItemOrdenes(List<com.tuempresa.ordenes.application.dto.ItemOrdenResponse> applicationResponses) {
        if ( applicationResponses == null ) {
            return null;
        }

        List<ItemOrdenResponse> list = new ArrayList<ItemOrdenResponse>( applicationResponses.size() );
        for ( com.tuempresa.ordenes.application.dto.ItemOrdenResponse itemOrdenResponse : applicationResponses ) {
            list.add( toRestResponse( itemOrdenResponse ) );
        }

        return list;
    }
}
