package com.tuempresa.ordenes.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Inventario(
    UUID id,
    UUID productoId,
    Integer cantidadDisponible,
    Integer cantidadReservada,
    Integer cantidadMinima,
    String ubicacion,
    LocalDateTime fechaActualizacion
) {
    
    public Inventario {
        if (id == null) {
            throw new IllegalArgumentException("El ID del inventario no puede ser nulo");
        }
        if (productoId == null) {
            throw new IllegalArgumentException("El ID del producto no puede ser nulo");
        }
        if (cantidadDisponible == null || cantidadDisponible < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }
        if (cantidadReservada == null || cantidadReservada < 0) {
            throw new IllegalArgumentException("La cantidad reservada no puede ser negativa");
        }
        if (cantidadMinima == null || cantidadMinima < 0) {
            throw new IllegalArgumentException("La cantidad mínima no puede ser negativa");
        }
    }
    
    public Integer getStockTotal() {
        return cantidadDisponible + cantidadReservada;
    }
    
    public boolean tieneStockDisponible() {
        return cantidadDisponible > 0;
    }
    
    public boolean necesitaReabastecimiento() {
        return cantidadDisponible <= cantidadMinima;
    }
    
    public boolean puedeReservar(Integer cantidad) {
        return cantidadDisponible >= cantidad;
    }
    
    public Inventario reservarStock(Integer cantidad) {
        if (!puedeReservar(cantidad)) {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }
        
        return new Inventario(
            id, productoId, 
            cantidadDisponible - cantidad,
            cantidadReservada + cantidad,
            cantidadMinima, ubicacion, LocalDateTime.now()
        );
    }
    
    public Inventario liberarReserva(Integer cantidad) {
        if (cantidadReservada < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock reservado");
        }
        
        return new Inventario(
            id, productoId,
            cantidadDisponible + cantidad,
            cantidadReservada - cantidad,
            cantidadMinima, ubicacion, LocalDateTime.now()
        );
    }
    
    public Inventario actualizarStock(Integer nuevaCantidadDisponible) {
        return new Inventario(
            id, productoId, nuevaCantidadDisponible,
            cantidadReservada, cantidadMinima, ubicacion, LocalDateTime.now()
        );
    }
} 