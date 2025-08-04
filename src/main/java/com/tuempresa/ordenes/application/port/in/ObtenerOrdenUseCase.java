package com.tuempresa.ordenes.application.port.in;

import com.tuempresa.ordenes.application.dto.OrdenData;

import java.util.List;
import java.util.UUID;

public interface ObtenerOrdenUseCase {
    
    OrdenData obtenerOrdenPorId(UUID ordenId);
    
    OrdenData obtenerOrdenPorNumero(String numeroOrden);
    
    List<OrdenData> obtenerTodasLasOrdenes();
    
    List<OrdenData> obtenerOrdenesPorCliente(String clienteId);
} 