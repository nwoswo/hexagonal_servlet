package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.EnvioMetodo;
import com.tuempresa.ordenes.domain.model.enums.EnvioEstado;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Envio(
    UUID id,
    UUID ordenId,
    String direccionDestino,
    String codigoPostal,
    String ciudad,
    String pais,
    EnvioMetodo metodo,
    EnvioEstado estado,
    BigDecimal costoEnvio,
    LocalDateTime fechaEstimadaEntrega,
    LocalDateTime fechaCreacion
) {
    
    public Envio {
        if (id == null) {
            throw new IllegalArgumentException("El ID del envío no puede ser nulo");
        }
        if (ordenId == null) {
            throw new IllegalArgumentException("El ID de la orden no puede ser nulo");
        }
        if (direccionDestino == null || direccionDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección de destino no puede estar vacía");
        }
        if (metodo == null) {
            throw new IllegalArgumentException("El método de envío no puede ser nulo");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del envío no puede ser nulo");
        }
        if (costoEnvio == null || costoEnvio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El costo de envío no puede ser negativo");
        }
    }
    
    public boolean estaPendiente() {
        return estado == EnvioEstado.PENDIENTE;
    }
    
    public boolean estaEnTransito() {
        return estado == EnvioEstado.EN_TRANSITO;
    }
    
    public boolean estaEntregado() {
        return estado == EnvioEstado.ENTREGADO;
    }
    
    public boolean estaRetrasado() {
        return estado == EnvioEstado.RETRASADO;
    }
    
    public Envio iniciarEnvio() {
        return new Envio(
            id, ordenId, direccionDestino, codigoPostal, ciudad, pais,
            metodo, EnvioEstado.EN_TRANSITO, costoEnvio, fechaEstimadaEntrega, fechaCreacion
        );
    }
    
    public Envio marcarEntregado() {
        return new Envio(
            id, ordenId, direccionDestino, codigoPostal, ciudad, pais,
            metodo, EnvioEstado.ENTREGADO, costoEnvio, fechaEstimadaEntrega, fechaCreacion
        );
    }
    
    public Envio marcarRetrasado() {
        return new Envio(
            id, ordenId, direccionDestino, codigoPostal, ciudad, pais,
            metodo, EnvioEstado.RETRASADO, costoEnvio, fechaEstimadaEntrega, fechaCreacion
        );
    }
} 