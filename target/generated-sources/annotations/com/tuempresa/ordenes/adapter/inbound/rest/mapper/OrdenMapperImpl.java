package com.tuempresa.ordenes.adapter.inbound.rest.mapper;

import com.tuempresa.ordenes.adapter.inbound.rest.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.OrdenResponse;
import com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.application.dto.CrearOrdenRequest;
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
public class OrdenMapperImpl implements OrdenMapper {

    @Override
    public CrearOrdenRequest toApplicationRequest(com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearOrdenRequest request) {
        if ( request == null ) {
            return null;
        }

        CrearOrdenRequest.CrearOrdenRequestBuilder crearOrdenRequest = CrearOrdenRequest.builder();

        crearOrdenRequest.items( toApplicationRequestList( request.getItems() ) );
        crearOrdenRequest.clienteId( request.getClienteId() );
        crearOrdenRequest.clienteNombre( request.getClienteNombre() );
        crearOrdenRequest.clienteEmail( request.getClienteEmail() );

        return crearOrdenRequest.build();
    }

    @Override
    public CrearItemOrdenRequest toApplicationRequest(com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest request) {
        if ( request == null ) {
            return null;
        }

        CrearItemOrdenRequest.CrearItemOrdenRequestBuilder crearItemOrdenRequest = CrearItemOrdenRequest.builder();

        crearItemOrdenRequest.productoId( request.getProductoId() );
        crearItemOrdenRequest.productoNombre( request.getProductoNombre() );
        crearItemOrdenRequest.productoDescripcion( request.getProductoDescripcion() );
        crearItemOrdenRequest.precioUnitario( request.getPrecioUnitario() );
        crearItemOrdenRequest.cantidad( request.getCantidad() );

        return crearItemOrdenRequest.build();
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

        OrdenResponse.OrdenResponseBuilder ordenResponse = OrdenResponse.builder();

        ordenResponse.id( applicationResponse.getId() );
        ordenResponse.numeroOrden( applicationResponse.getNumeroOrden() );
        ordenResponse.clienteId( applicationResponse.getClienteId() );
        ordenResponse.clienteNombre( applicationResponse.getClienteNombre() );
        ordenResponse.clienteEmail( applicationResponse.getClienteEmail() );
        ordenResponse.total( applicationResponse.getTotal() );
        ordenResponse.estado( applicationResponse.getEstado() );
        ordenResponse.fechaCreacion( applicationResponse.getFechaCreacion() );
        ordenResponse.fechaActualizacion( applicationResponse.getFechaActualizacion() );
        ordenResponse.items( toRestResponseListItemOrdenes( applicationResponse.getItems() ) );

        return ordenResponse.build();
    }

    @Override
    public ItemOrdenResponse toRestResponse(com.tuempresa.ordenes.application.dto.ItemOrdenResponse applicationResponse) {
        if ( applicationResponse == null ) {
            return null;
        }

        ItemOrdenResponse.ItemOrdenResponseBuilder itemOrdenResponse = ItemOrdenResponse.builder();

        itemOrdenResponse.id( applicationResponse.getId() );
        itemOrdenResponse.ordenId( applicationResponse.getOrdenId() );
        itemOrdenResponse.productoId( applicationResponse.getProductoId() );
        itemOrdenResponse.productoNombre( applicationResponse.getProductoNombre() );
        itemOrdenResponse.productoDescripcion( applicationResponse.getProductoDescripcion() );
        itemOrdenResponse.precioUnitario( applicationResponse.getPrecioUnitario() );
        itemOrdenResponse.cantidad( applicationResponse.getCantidad() );
        itemOrdenResponse.subtotal( applicationResponse.getSubtotal() );
        itemOrdenResponse.fechaCreacion( applicationResponse.getFechaCreacion() );
        itemOrdenResponse.fechaActualizacion( applicationResponse.getFechaActualizacion() );

        return itemOrdenResponse.build();
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
