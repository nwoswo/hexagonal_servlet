package com.tuempresa.ordenes.domain.model;

import com.tuempresa.ordenes.domain.model.enums.NotificacionTipo;
import com.tuempresa.ordenes.domain.model.enums.NotificacionEstado;
import java.time.LocalDateTime;
import java.util.UUID;

public record Notificacion(
    UUID id,
    UUID entidadId,
    String entidadTipo,
    NotificacionTipo tipo,
    String destinatario,
    String asunto,
    String contenido,
    NotificacionEstado estado,
    LocalDateTime fechaEnvio,
    LocalDateTime fechaCreacion
) {
    
    public Notificacion {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la notificación no puede ser nulo");
        }
        if (entidadId == null) {
            throw new IllegalArgumentException("El ID de la entidad no puede ser nulo");
        }
        if (entidadTipo == null || entidadTipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de entidad no puede estar vacío");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo");
        }
        if (destinatario == null || destinatario.trim().isEmpty()) {
            throw new IllegalArgumentException("El destinatario no puede estar vacío");
        }
        if (asunto == null || asunto.trim().isEmpty()) {
            throw new IllegalArgumentException("El asunto no puede estar vacío");
        }
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado de la notificación no puede ser nulo");
        }
    }
    
    public boolean estaPendiente() {
        return estado == NotificacionEstado.PENDIENTE;
    }
    
    public boolean estaEnviada() {
        return estado == NotificacionEstado.ENVIADA;
    }
    
    public boolean falloEnvio() {
        return estado == NotificacionEstado.FALLIDA;
    }
    
    public Notificacion marcarEnviada() {
        return new Notificacion(
            id, entidadId, entidadTipo, tipo, destinatario, asunto, contenido,
            NotificacionEstado.ENVIADA, LocalDateTime.now(), fechaCreacion
        );
    }
    
    public Notificacion marcarFallida(String motivo) {
        return new Notificacion(
            id, entidadId, entidadTipo, tipo, destinatario, asunto, contenido + " - Error: " + motivo,
            NotificacionEstado.FALLIDA, null, fechaCreacion
        );
    }
    
    public boolean esEmail() {
        return tipo == NotificacionTipo.EMAIL;
    }
    
    public boolean esSMS() {
        return tipo == NotificacionTipo.SMS;
    }
    
    public boolean esPush() {
        return tipo == NotificacionTipo.PUSH;
    }
} 