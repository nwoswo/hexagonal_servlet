package com.tuempresa.ordenes.adapter.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearItemOrdenRequest {
    
    @NotBlank(message = "El ID del producto es requerido")
    private String productoId;
    
    @NotBlank(message = "El nombre del producto es requerido")
    private String productoNombre;
    
    private String productoDescripcion;
    
    @NotNull(message = "El precio unitario es requerido")
    @Min(value = 0, message = "El precio unitario debe ser mayor o igual a 0")
    private BigDecimal precioUnitario;
    
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
} 