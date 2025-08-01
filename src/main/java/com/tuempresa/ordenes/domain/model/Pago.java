package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.PagoMetodo;
import com.tuempresa.ordenes.domain.model.enums.PagoEstado;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Pago(
    UUID id,
    UUID ordenId,
    BigDecimal monto,
    PagoMetodo metodo,
    PagoEstado estado,
    String referencia,
    LocalDateTime fechaPago,
    LocalDateTime fechaCreacion
) {
    
    public Pago {
        if (id == null) {
            throw new IllegalArgumentException("El ID del pago no puede ser nulo");
        }
        if (ordenId == null) {
            throw new IllegalArgumentException("El ID de la orden no puede ser nulo");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero");
        }
        if (metodo == null) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del pago no puede ser nulo");
        }
    }
    
    public boolean estaAprobado() {
        return estado == PagoEstado.APROBADO;
    }
    
    public boolean estaPendiente() {
        return estado == PagoEstado.PENDIENTE;
    }
    
    public boolean estaRechazado() {
        return estado == PagoEstado.RECHAZADO;
    }
    
    public Pago aprobar(String referenciaPago) {
        return new Pago(
            id, ordenId, monto, metodo, PagoEstado.APROBADO,
            referenciaPago, LocalDateTime.now(), fechaCreacion
        );
    }
    
    public Pago rechazar(String motivo) {
        return new Pago(
            id, ordenId, monto, metodo, PagoEstado.RECHAZADO,
            motivo, null, fechaCreacion
        );
    }
} 