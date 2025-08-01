package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.application.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.application.port.in.AgregarItemOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.application.port.out.ItemOrdenRepository;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.event.ItemOrdenCreadoEvent;
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
    
    @Override
    public ItemOrdenResponse agregarItemAOrden(UUID ordenId, CrearItemOrdenRequest request) {
        // Verificar que la orden existe
        Orden orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        // Crear el item
        ItemOrden item = ItemOrden.builder()
                .id(UUID.randomUUID())
                .ordenId(ordenId)
                .productoId(request.productoId())
                .productoNombre(request.productoNombre())
                .productoDescripcion(request.productoDescripcion())
                .precioUnitario(BigDecimal.valueOf(request.precioUnitario()))
                .cantidad(request.cantidad())
                .subtotal(BigDecimal.valueOf(request.precioUnitario() * request.cantidad()))
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
        
        // Guardar el item
        ItemOrden itemGuardado = itemOrdenRepository.guardar(item);
        
        // Actualizar total de la orden
        BigDecimal nuevoTotal = orden.getTotal().add(itemGuardado.getSubtotal());
        Orden ordenActualizada = orden.toBuilder()
                .total(nuevoTotal)
                .fechaActualizacion(LocalDateTime.now())
                .build();
        
        ordenRepository.guardar(ordenActualizada);
        
        // Publicar evento
        eventPublisher.publicarEvento(new ItemOrdenCreadoEvent(itemGuardado));
        
        return convertirAItemOrdenResponse(itemGuardado);
    }
    
    private ItemOrdenResponse convertirAItemOrdenResponse(ItemOrden item) {
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