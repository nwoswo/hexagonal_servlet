package com.tuempresa.ordenes.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistorialCambio(
    UUID id,
    String entidadTipo,
    UUID entidadId,
    String campo,
    String valorAnterior,
    String valorNuevo,
    String usuario,
    LocalDateTime fechaCambio
) {
    
    public HistorialCambio {
        if (id == null) {
            throw new IllegalArgumentException("El ID del historial no puede ser nulo");
        }
        if (entidadTipo == null || entidadTipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de entidad no puede estar vacío");
        }
        if (entidadId == null) {
            throw new IllegalArgumentException("El ID de la entidad no puede ser nulo");
        }
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo no puede estar vacío");
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío");
        }
    }
    
    public boolean esCambioSignificativo() {
        return valorAnterior != null && valorNuevo != null && !valorAnterior.equals(valorNuevo);
    }
    
    public String getDescripcionCambio() {
        return String.format("Campo '%s' cambió de '%s' a '%s'", campo, valorAnterior, valorNuevo);
    }
} 