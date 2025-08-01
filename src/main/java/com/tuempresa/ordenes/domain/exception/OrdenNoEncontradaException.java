package com.tuempresa.ordenes.domain.exception;

import java.util.UUID;

public class OrdenNoEncontradaException extends OrdenException {
    
    public OrdenNoEncontradaException(UUID ordenId) {
        super("Orden no encontrada con ID: " + ordenId);
    }
    
    public OrdenNoEncontradaException(String numeroOrden) {
        super("Orden no encontrada con número: " + numeroOrden);
    }
} 