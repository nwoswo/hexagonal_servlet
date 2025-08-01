package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.ResenaEstado;
import java.time.LocalDateTime;
import java.util.UUID;

public record Resena(
    UUID id,
    UUID ordenId,
    UUID clienteId,
    Integer calificacion,
    String comentario,
    ResenaEstado estado,
    LocalDateTime fechaCreacion
) {
    
    public Resena {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la reseña no puede ser nulo");
        }
        if (ordenId == null) {
            throw new IllegalArgumentException("El ID de la orden no puede ser nulo");
        }
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        if (calificacion == null || calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado de la reseña no puede ser nulo");
        }
    }
    
    public boolean estaAprobada() {
        return estado == ResenaEstado.APROBADA;
    }
    
    public boolean estaPendiente() {
        return estado == ResenaEstado.PENDIENTE;
    }
    
    public boolean estaRechazada() {
        return estado == ResenaEstado.RECHAZADA;
    }
    
    public boolean esCalificacionAlta() {
        return calificacion >= 4;
    }
    
    public boolean esCalificacionBaja() {
        return calificacion <= 2;
    }
    
    public Resena aprobar() {
        return new Resena(
            id, ordenId, clienteId, calificacion, comentario,
            ResenaEstado.APROBADA, fechaCreacion
        );
    }
    
    public Resena rechazar(String motivo) {
        return new Resena(
            id, ordenId, clienteId, calificacion, comentario + " - Rechazada: " + motivo,
            ResenaEstado.RECHAZADA, fechaCreacion
        );
    }
    
    public String getCalificacionEstrellas() {
        return "★".repeat(calificacion) + "☆".repeat(5 - calificacion);
    }
} 