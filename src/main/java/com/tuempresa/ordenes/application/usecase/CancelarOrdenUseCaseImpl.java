package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.port.in.CancelarOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.exception.OrdenNoPuedeSerCanceladaException;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.model.OrdenEstado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelarOrdenUseCaseImpl implements CancelarOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    
    @Override
    public void cancelarOrden(UUID ordenId, String motivo) {
        Orden orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        if (orden.getEstado() == OrdenEstado.CANCELADA) {
            throw new OrdenNoPuedeSerCanceladaException(ordenId);
        }
        
        if (orden.getEstado() == OrdenEstado.ENTREGADA) {
            throw new OrdenNoPuedeSerCanceladaException(ordenId);
        }
        
        Orden ordenCancelada = orden.toBuilder()
                .estado(OrdenEstado.CANCELADA)
                .fechaActualizacion(LocalDateTime.now())
                .build();
        
        ordenRepository.guardar(ordenCancelada);
    }
} 