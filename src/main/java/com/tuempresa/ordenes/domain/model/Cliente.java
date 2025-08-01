package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.ClienteTipo;
import com.tuempresa.ordenes.domain.model.enums.ClienteEstado;
import java.time.LocalDateTime;
import java.util.UUID;

public record Cliente(
    UUID id,
    String codigo,
    String nombre,
    String email,
    String telefono,
    String direccion,
    ClienteTipo tipo,
    ClienteEstado estado,
    LocalDateTime fechaRegistro,
    LocalDateTime fechaActualizacion
) {
    
    public Cliente {
        if (id == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del cliente no puede estar vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email del cliente no puede estar vacío");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de cliente no puede ser nulo");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del cliente no puede ser nulo");
        }
    }
    
    public boolean esActivo() {
        return estado == ClienteEstado.ACTIVO;
    }
    
    public boolean esEmpresa() {
        return tipo == ClienteTipo.EMPRESA;
    }
    
    public boolean esIndividual() {
        return tipo == ClienteTipo.INDIVIDUAL;
    }
    
    public Cliente actualizarEstado(ClienteEstado nuevoEstado) {
        return new Cliente(
            id, codigo, nombre, email, telefono, direccion,
            tipo, nuevoEstado, fechaRegistro, LocalDateTime.now()
        );
    }
    
    public Cliente actualizarInformacion(String nuevoNombre, String nuevoEmail, String nuevoTelefono, String nuevaDireccion) {
        return new Cliente(
            id, codigo, nuevoNombre, nuevoEmail, nuevoTelefono, nuevaDireccion,
            tipo, estado, fechaRegistro, LocalDateTime.now()
        );
    }
} 