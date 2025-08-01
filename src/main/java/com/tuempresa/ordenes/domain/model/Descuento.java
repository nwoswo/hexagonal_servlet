package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.DescuentoTipo;
import com.tuempresa.ordenes.domain.model.enums.DescuentoEstado;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Descuento(
    UUID id,
    String codigo,
    String descripcion,
    DescuentoTipo tipo,
    BigDecimal valor,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin,
    Integer usoMaximo,
    Integer usoActual,
    DescuentoEstado estado
) {
    
    public Descuento {
        if (id == null) {
            throw new IllegalArgumentException("El ID del descuento no puede ser nulo");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del descuento no puede estar vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del descuento no puede estar vacía");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de descuento no puede ser nulo");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor del descuento debe ser mayor a cero");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del descuento no puede ser nulo");
        }
    }
    
    public boolean estaActivo() {
        return estado == DescuentoEstado.ACTIVO;
    }
    
    public boolean estaVigente() {
        LocalDateTime ahora = LocalDateTime.now();
        return fechaInicio.isBefore(ahora) && fechaFin.isAfter(ahora);
    }
    
    public boolean tieneUsosDisponibles() {
        return usoMaximo == null || usoActual < usoMaximo;
    }
    
    public boolean puedeUsar() {
        return estaActivo() && estaVigente() && tieneUsosDisponibles();
    }
    
    public Descuento incrementarUso() {
        int nuevoUso = usoActual + 1;
        DescuentoEstado nuevoEstado = (usoMaximo != null && nuevoUso >= usoMaximo) 
            ? DescuentoEstado.AGOTADO : estado;
        
        return new Descuento(
            id, codigo, descripcion, tipo, valor, fechaInicio, fechaFin,
            usoMaximo, nuevoUso, nuevoEstado
        );
    }
    
    public BigDecimal calcularDescuento(BigDecimal montoBase) {
        if (tipo == DescuentoTipo.PORCENTAJE) {
            return montoBase.multiply(valor).divide(BigDecimal.valueOf(100));
        } else {
            return valor;
        }
    }
} 