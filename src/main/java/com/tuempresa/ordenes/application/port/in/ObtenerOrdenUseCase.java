package com.tuempresa.ordenes.application.port.in;

import com.tuempresa.ordenes.application.dto.OrdenResponse;

import java.util.List;
import java.util.UUID;

public interface ObtenerOrdenUseCase {
    
    OrdenResponse obtenerOrdenPorId(UUID ordenId);
    
    OrdenResponse obtenerOrdenPorNumero(String numeroOrden);
    
    List<OrdenResponse> obtenerTodasLasOrdenes();
    
    List<OrdenResponse> obtenerOrdenesPorCliente(String clienteId);
} 