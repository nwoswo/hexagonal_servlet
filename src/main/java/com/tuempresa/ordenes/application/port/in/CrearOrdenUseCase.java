package com.tuempresa.ordenes.application.port.in;

import com.tuempresa.ordenes.application.dto.CrearOrdenCommand;
import com.tuempresa.ordenes.application.dto.OrdenData;

import java.util.UUID;

public interface CrearOrdenUseCase {
    
    OrdenData crearOrden(CrearOrdenCommand request);
} 