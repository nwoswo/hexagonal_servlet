package com.tuempresa.ordenes.domain.exception;

import java.util.UUID;

public class OrdenNoPuedeSerCanceladaException extends OrdenException {
    
    public OrdenNoPuedeSerCanceladaException(UUID ordenId) {
        super("La orden con ID " + ordenId + " no puede ser cancelada en su estado actual");
    }
} 