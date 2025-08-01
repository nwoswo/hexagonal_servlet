package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.ProductoEstado;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Producto(
    UUID id,
    String codigo,
    String nombre,
    String descripcion,
    BigDecimal precio,
    String categoria,
    Integer stockDisponible,
    ProductoEstado estado,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {
    
    public Producto {
        if (id == null) {
            throw new IllegalArgumentException("El ID del producto no puede ser nulo");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del producto no puede estar vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor o igual a cero");
        }
        if (stockDisponible == null || stockDisponible < 0) {
            throw new IllegalArgumentException("El stock disponible no puede ser negativo");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del producto no puede ser nulo");
        }
    }
    
    public boolean estaActivo() {
        return estado == ProductoEstado.ACTIVO;
    }
    
    public boolean tieneStock() {
        return stockDisponible > 0;
    }
    
    public boolean estaAgotado() {
        return estado == ProductoEstado.AGOTADO;
    }
    
    public Producto actualizarStock(Integer nuevoStock) {
        ProductoEstado nuevoEstado = nuevoStock > 0 ? ProductoEstado.ACTIVO : ProductoEstado.AGOTADO;
        return new Producto(
            id, codigo, nombre, descripcion, precio, categoria,
            nuevoStock, nuevoEstado, fechaCreacion, LocalDateTime.now()
        );
    }
    
    public Producto actualizarPrecio(BigDecimal nuevoPrecio) {
        return new Producto(
            id, codigo, nombre, descripcion, nuevoPrecio, categoria,
            stockDisponible, estado, fechaCreacion, LocalDateTime.now()
        );
    }
    
    public Producto actualizarEstado(ProductoEstado nuevoEstado) {
        return new Producto(
            id, codigo, nombre, descripcion, precio, categoria,
            stockDisponible, nuevoEstado, fechaCreacion, LocalDateTime.now()
        );
    }
    
    public boolean puedeComprar(Integer cantidad) {
        return estaActivo() && tieneStock() && stockDisponible >= cantidad;
    }
} 