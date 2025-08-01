package com.tuempresa.ordenes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrden {
    
    private UUID id;
    private UUID ordenId;
    private String productoId;
    private String productoNombre;
    private String productoDescripcion;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal subtotal;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    public ItemOrden(UUID ordenId, String productoId, String productoNombre, 
                    String productoDescripcion, BigDecimal precioUnitario, Integer cantidad) {
        this.id = UUID.randomUUID();
        this.ordenId = ordenId;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.productoDescripcion = productoDescripcion;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        calcularSubtotal();
    }
    
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
    
    public void actualizarCantidad(Integer nuevaCantidad) {
        if (nuevaCantidad > 0) {
            this.cantidad = nuevaCantidad;
            calcularSubtotal();
            this.fechaActualizacion = LocalDateTime.now();
        }
    }
    
    public void actualizarPrecio(BigDecimal nuevoPrecio) {
        if (nuevoPrecio.compareTo(BigDecimal.ZERO) > 0) {
            this.precioUnitario = nuevoPrecio;
            calcularSubtotal();
            this.fechaActualizacion = LocalDateTime.now();
        }
    }
    
    public boolean esValido() {
        return productoId != null && !productoId.isEmpty() &&
               cantidad != null && cantidad > 0 &&
               precioUnitario != null && precioUnitario.compareTo(BigDecimal.ZERO) > 0;
    }
} 