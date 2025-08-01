package com.tuempresa.ordenes.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearItemOrdenRequest(
    @NotBlank(message = "El ID del producto es requerido") String productoId,
    @NotBlank(message = "El nombre del producto es requerido") String productoNombre,
    String productoDescripcion,
    @NotNull(message = "El precio unitario es requerido") 
    @Min(value = 0, message = "El precio unitario debe ser mayor o igual a 0") Double precioUnitario,
    @NotNull(message = "La cantidad es requerida") 
    @Min(value = 1, message = "La cantidad debe ser mayor a 0") Integer cantidad
) {} 