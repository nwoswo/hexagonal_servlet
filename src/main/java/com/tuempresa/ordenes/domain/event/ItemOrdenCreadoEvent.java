package com.tuempresa.ordenes.domain.event;

import com.tuempresa.ordenes.domain.model.ItemOrden;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemOrdenCreadoEvent extends OrdenEvent {
    
    private UUID itemId;
    private UUID ordenId;
    private String productoId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    
    public ItemOrdenCreadoEvent(ItemOrden item) {
        super("ITEM_ORDEN_CREADO");
        this.itemId = item.getId();
        this.ordenId = item.getOrdenId();
        this.productoId = item.getProductoId();
        this.productoNombre = item.getProductoNombre();
        this.cantidad = item.getCantidad();
        this.precioUnitario = item.getPrecioUnitario();
        this.subtotal = item.getSubtotal();
    }
} 