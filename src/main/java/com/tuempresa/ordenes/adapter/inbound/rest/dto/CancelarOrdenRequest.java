package com.tuempresa.ordenes.adapter.inbound.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CancelarOrdenRequest(
    @NotBlank(message = "El motivo de cancelación es requerido") String motivo
) {} 