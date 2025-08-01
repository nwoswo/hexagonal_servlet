package com.tuempresa.ordenes.adapter.inbound.rest.dto;

import com.tuempresa.ordenes.domain.model.OrdenEstado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenResponse {
    
    private UUID id;
    private String numeroOrden;
    private String clienteId;
    private String clienteNombre;
    private String clienteEmail;
    private BigDecimal total;
    private OrdenEstado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<ItemOrdenResponse> items;
} 