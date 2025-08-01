package com.tuempresa.ordenes.application.port.in;

import java.util.UUID;

public interface CancelarOrdenUseCase {
    
    void cancelarOrden(UUID ordenId, String motivo);
} 