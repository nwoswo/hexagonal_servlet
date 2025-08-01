package com.tuempresa.ordenes.domain.service;

import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.exception.OrdenNoPuedeSerCanceladaException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrdenDomainService {
    
    public void validarOrden(Orden orden) {
        if (orden == null) {
            throw new IllegalArgumentException("La orden no puede ser nula");
        }
        
        if (orden.getClienteId() == null || orden.getClienteId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del cliente es requerido");
        }
        
        if (orden.getClienteNombre() == null || orden.getClienteNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es requerido");
        }
    }
    
    public void validarItemOrden(ItemOrden item) {
        if (item == null) {
            throw new IllegalArgumentException("El item de orden no puede ser nulo");
        }
        
        if (!item.esValido()) {
            throw new IllegalArgumentException("El item de orden no es válido");
        }
    }
    
    public void validarCancelacion(Orden orden) {
        if (!orden.puedeSerCancelada()) {
            throw new OrdenNoPuedeSerCanceladaException(orden.getId());
        }
    }
    
    public void agregarItemAOrden(Orden orden, ItemOrden item) {
        validarOrden(orden);
        validarItemOrden(item);
        
        // Verificar que el item pertenece a la orden
        if (!item.getOrdenId().equals(orden.getId())) {
            throw new IllegalArgumentException("El item no pertenece a la orden");
        }
        
        orden.agregarItem(item);
    }
    
    public void removerItemDeOrden(Orden orden, UUID itemId) {
        validarOrden(orden);
        
        if (itemId == null) {
            throw new IllegalArgumentException("El ID del item es requerido");
        }
        
        orden.removerItem(itemId);
    }
    
    public void confirmarOrden(Orden orden) {
        validarOrden(orden);
        
        if (!orden.tieneItems()) {
            throw new IllegalArgumentException("No se puede confirmar una orden sin items");
        }
        
        orden.confirmar();
    }
    
    public void cancelarOrden(Orden orden, String motivo) {
        validarOrden(orden);
        validarCancelacion(orden);
        
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de cancelación es requerido");
        }
        
        orden.cancelar();
    }
} 