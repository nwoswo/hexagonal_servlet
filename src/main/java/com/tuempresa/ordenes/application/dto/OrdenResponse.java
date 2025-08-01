package com.tuempresa.ordenes.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrdenResponse(
    UUID id,
    String numeroOrden,
    String clienteId,
    String clienteNombre,
    String clienteEmail,
    BigDecimal total,
    String estado,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion,
    List<ItemOrdenResponse> items
) {} 