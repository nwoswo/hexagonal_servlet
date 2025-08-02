package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.port.in.CancelarOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.domain.event.OrdenCanceladaEvent;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.exception.OrdenNoPuedeSerCanceladaException;
import com.tuempresa.ordenes.domain.model.Orden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelarOrdenUseCaseImpl implements CancelarOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final EventPublisher eventPublisher;
    
    @Override
    public void cancelarOrden(UUID ordenId, String motivo) {
        Orden orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        // Usar el método del dominio para validar si puede ser cancelada
        if (!orden.puedeSerCancelada()) {
            throw new OrdenNoPuedeSerCanceladaException(ordenId);
        }
        
        // Usar el método del dominio para cancelar la orden
        orden.cancelar();
        
        // Guardar la orden cancelada
        ordenRepository.guardar(orden);
        
        // Publicar evento de cancelación
        eventPublisher.publicarEvento(new OrdenCanceladaEvent(orden, motivo));
    }
} 