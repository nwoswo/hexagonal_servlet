package com.tuempresa.ordenes.domain.event;

import com.tuempresa.ordenes.domain.model.Orden;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdenCanceladaEvent extends OrdenEvent {
    
    private UUID ordenId;
    private String numeroOrden;
    private String clienteId;
    private String motivo;
    
    public OrdenCanceladaEvent(Orden orden, String motivo) {
        super("ORDEN_CANCELADA");
        this.ordenId = orden.getId();
        this.numeroOrden = orden.getNumeroOrden();
        this.clienteId = orden.getClienteId();
        this.motivo = motivo;
    }
} 