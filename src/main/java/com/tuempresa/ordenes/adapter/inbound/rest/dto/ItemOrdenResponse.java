package com.tuempresa.ordenes.adapter.inbound.rest.dto;

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
public class ItemOrdenResponse {
    
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
} 