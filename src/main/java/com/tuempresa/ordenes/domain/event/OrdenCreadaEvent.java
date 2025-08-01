package com.tuempresa.ordenes.domain.event;

import com.tuempresa.ordenes.domain.model.Orden;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdenCreadaEvent extends OrdenEvent {
    
    private UUID ordenId;
    private String numeroOrden;
    private String clienteId;
    private String clienteNombre;
    private BigDecimal total;
    
    public OrdenCreadaEvent(Orden orden) {
        super("ORDEN_CREADA");
        this.ordenId = orden.getId();
        this.numeroOrden = orden.getNumeroOrden();
        this.clienteId = orden.getClienteId();
        this.clienteNombre = orden.getClienteNombre();
        this.total = orden.getTotal();
    }
} 