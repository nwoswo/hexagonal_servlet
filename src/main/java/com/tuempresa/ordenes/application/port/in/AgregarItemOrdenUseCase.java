package com.tuempresa.ordenes.application.port.in;

import com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.application.dto.ItemOrdenResponse;

import java.util.UUID;

public interface AgregarItemOrdenUseCase {
    
    ItemOrdenResponse agregarItemAOrden(UUID ordenId, CrearItemOrdenRequest request);
} 