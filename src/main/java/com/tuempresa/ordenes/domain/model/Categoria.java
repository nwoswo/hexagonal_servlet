package com.tuempresa.ordenes.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Categoria(
    UUID id,
    String codigo,
    String nombre,
    String descripcion,
    UUID categoriaPadreId,
    LocalDateTime fechaCreacion
) {
    
    public Categoria {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de la categoría no puede estar vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
    }
    
    public boolean esCategoriaRaiz() {
        return categoriaPadreId == null;
    }
    
    public boolean esSubcategoria() {
        return categoriaPadreId != null;
    }
} 