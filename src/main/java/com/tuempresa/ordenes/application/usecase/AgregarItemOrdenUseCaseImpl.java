package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.application.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.application.port.in.AgregarItemOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.application.port.out.ItemOrdenRepository;
import com.tuempresa.ordenes.domain.event.ItemOrdenCreadoEvent;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.service.OrdenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgregarItemOrdenUseCaseImpl implements AgregarItemOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;
    private final EventPublisher eventPublisher;
    private final OrdenDomainService ordenDomainService;
    
    @Override
    public ItemOrdenResponse agregarItemAOrden(UUID ordenId, CrearItemOrdenRequest request) {
        var orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        var item = new ItemOrden(
            UUID.randomUUID(),
            ordenId,
            request.productoId(),
            request.productoNombre(),
            request.productoDescripcion(),
            BigDecimal.valueOf(request.precioUnitario()),
            request.cantidad(),
            BigDecimal.ZERO,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        
        ordenDomainService.agregarItemAOrden(orden, item);
        
        ordenRepository.guardar(orden);
        itemOrdenRepository.guardar(item);
        
        eventPublisher.publicarEvento(new ItemOrdenCreadoEvent(item));
        
        return new ItemOrdenResponse(
            item.getId(),
            item.getProductoId(),
            item.getProductoNombre(),
            item.getProductoDescripcion(),
            item.getPrecioUnitario(),
            item.getCantidad(),
            item.getSubtotal(),
            item.getFechaCreacion(),
            item.getFechaActualizacion()
        );
    }
} 