package com.tuempresa.ordenes.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ItemOrdenData(
    UUID id,
    String productoId,
    String productoNombre,
    String productoDescripcion,
    BigDecimal precioUnitario,
    Integer cantidad,
    BigDecimal subtotal,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {} 