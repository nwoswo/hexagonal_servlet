package com.tuempresa.ordenes.application.port.in;

import com.tuempresa.ordenes.application.dto.CrearItemOrdenCommand;
import com.tuempresa.ordenes.application.dto.ItemOrdenData;

import java.util.UUID;

public interface AgregarItemOrdenUseCase {
    
    ItemOrdenData agregarItemAOrden(UUID ordenId, CrearItemOrdenCommand request);
} 