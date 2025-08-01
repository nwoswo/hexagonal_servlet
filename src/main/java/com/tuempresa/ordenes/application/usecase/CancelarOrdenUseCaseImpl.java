package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.port.in.CancelarOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.event.OrdenCanceladaEvent;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.service.OrdenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelarOrdenUseCaseImpl implements CancelarOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final EventPublisher eventPublisher;
    private final OrdenDomainService ordenDomainService;
    
    @Override
    public void cancelarOrden(UUID ordenId, String motivo) {
        // Buscar la orden
        Orden orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        // Cancelar la orden usando el servicio de dominio
        ordenDomainService.cancelarOrden(orden, motivo);
        
        // Guardar la orden actualizada
        ordenRepository.guardar(orden);
        
        // Publicar evento de cancelación
        eventPublisher.publicarEvento(new OrdenCanceladaEvent(orden, motivo));
    }
} 