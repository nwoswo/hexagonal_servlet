package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearItemOrdenCommand;
import com.tuempresa.ordenes.application.dto.ItemOrdenData;
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
    public ItemOrdenData agregarItemAOrden(UUID ordenId, CrearItemOrdenCommand request) {
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
        
        // Usar el método del dominio para agregar el item a la orden
        orden.agregarItem(itemGuardado);
        
        // Guardar la orden actualizada
        ordenRepository.guardar(orden);
        
        // Publicar evento
        eventPublisher.publicarEvento(new ItemOrdenCreadoEvent(itemGuardado));
        
        return convertirAItemOrdenResponse(itemGuardado);
    }
    
    private ItemOrdenData convertirAItemOrdenResponse(ItemOrden item) {
        return new ItemOrdenData(
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