package com.tuempresa.ordenes.application.port.in;

import com.tuempresa.ordenes.application.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.application.dto.OrdenResponse;

import java.util.UUID;

public interface CrearOrdenUseCase {
    
    OrdenResponse crearOrden(CrearOrdenRequest request);
} 