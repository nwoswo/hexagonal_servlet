package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.application.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.application.port.in.AgregarItemOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.event.ItemOrdenCreadoEvent;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.service.OrdenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgregarItemOrdenUseCaseImpl implements AgregarItemOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final EventPublisher eventPublisher;
    private final OrdenDomainService ordenDomainService;
    
    @Override
    public ItemOrdenResponse agregarItemAOrden(UUID ordenId, CrearItemOrdenRequest request) {
        // Buscar la orden
        Orden orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        // Crear el item
        ItemOrden item = new ItemOrden(
            ordenId,
            request.getProductoId(),
            request.getProductoNombre(),
            request.getProductoDescripcion(),
            request.getPrecioUnitario(),
            request.getCantidad()
        );
        
        // Validar el item
        ordenDomainService.validarItemOrden(item);
        
        // Agregar el item a la orden
        ordenDomainService.agregarItemAOrden(orden, item);
        
        // Guardar la orden actualizada
        ordenRepository.guardar(orden);
        
        // Publicar evento
        eventPublisher.publicarEvento(new ItemOrdenCreadoEvent(item));
        
        // Convertir a DTO de respuesta
        return convertirAItemOrdenResponse(item);
    }
    
    private ItemOrdenResponse convertirAItemOrdenResponse(ItemOrden item) {
        return ItemOrdenResponse.builder()
                .id(item.getId())
                .ordenId(item.getOrdenId())
                .productoId(item.getProductoId())
                .productoNombre(item.getProductoNombre())
                .productoDescripcion(item.getProductoDescripcion())
                .precioUnitario(item.getPrecioUnitario())
                .cantidad(item.getCantidad())
                .subtotal(item.getSubtotal())
                .fechaCreacion(item.getFechaCreacion())
                .fechaActualizacion(item.getFechaActualizacion())
                .build();
    }
} 