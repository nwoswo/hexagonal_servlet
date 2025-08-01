package com.tuempresa.ordenes.adapter.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelarOrdenRequest {
    
    @NotBlank(message = "El motivo de cancelación es requerido")
    private String motivo;
} 