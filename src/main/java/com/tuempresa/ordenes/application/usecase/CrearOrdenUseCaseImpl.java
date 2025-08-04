package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearOrdenCommand;
import com.tuempresa.ordenes.application.dto.OrdenData;
import com.tuempresa.ordenes.application.port.in.CrearOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.application.port.out.ItemOrdenRepository;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.model.OrdenEstado;
import com.tuempresa.ordenes.domain.event.OrdenCreadaEvent;
import com.tuempresa.ordenes.domain.event.ItemOrdenCreadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CrearOrdenUseCaseImpl implements CrearOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;
    private final EventPublisher eventPublisher;
    
    @Override
    public OrdenData crearOrden(CrearOrdenCommand request) {
        // Crear la orden
        Orden orden = Orden.builder()
                .id(UUID.randomUUID())
                .numeroOrden(generarNumeroOrden())
                .clienteId(request.clienteId())
                .clienteNombre(request.clienteNombre())
                .clienteEmail(request.clienteEmail())
                .estado(OrdenEstado.PENDIENTE)
                .total(BigDecimal.ZERO)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
        
        // Guardar la orden
        Orden ordenGuardada = ordenRepository.guardar(orden);
        final UUID ordenId = ordenGuardada.getId();
        
        // Procesar items si existen
        final List<ItemOrden> itemsGuardados =
            (request.items() != null && !request.items().isEmpty())
                ? request.items().stream()
                    .map(itemRequest -> crearItemOrden(ordenId, itemRequest))
                    .collect(Collectors.toList())
                : List.of();
        
        // Agregar items a la orden usando el método del dominio
        itemsGuardados.forEach(ordenGuardada::agregarItem);
        
        Orden ordenFinal = ordenRepository.guardar(ordenGuardada);
        
        // Publicar eventos
        eventPublisher.publicarEvento(new OrdenCreadaEvent(ordenFinal));
        
        // Publicar eventos de items solo si la orden tiene items
        if (ordenFinal.tieneItems()) {
            itemsGuardados.forEach(item -> 
                eventPublisher.publicarEvento(new ItemOrdenCreadoEvent(item))
            );
        }
        
        return convertirAOrdenResponse(ordenFinal);
    }
    
    private ItemOrden crearItemOrden(UUID ordenId, com.tuempresa.ordenes.application.dto.CrearItemOrdenCommand itemRequest) {
        ItemOrden item = ItemOrden.builder()
                .id(UUID.randomUUID())
                .ordenId(ordenId)
                .productoId(itemRequest.productoId())
                .productoNombre(itemRequest.productoNombre())
                .productoDescripcion(itemRequest.productoDescripcion())
                .precioUnitario(BigDecimal.valueOf(itemRequest.precioUnitario()))
                .cantidad(itemRequest.cantidad())
                .subtotal(BigDecimal.valueOf(itemRequest.precioUnitario() * itemRequest.cantidad()))
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
        
        // Validar que el item sea válido usando el método del dominio
        if (!item.esValido()) {
            throw new IllegalArgumentException("El item de orden no es válido");
        }
        
        return itemOrdenRepository.guardar(item);
    }
    
    private String generarNumeroOrden() {
        return "ORD-" + System.currentTimeMillis();
    }
    
    private OrdenData convertirAOrdenResponse(Orden orden) {
        return new OrdenData(
            orden.getId(),
            orden.getNumeroOrden(),
            orden.getClienteId(),
            orden.getClienteNombre(),
            orden.getClienteEmail(),
            orden.getTotal(),
            orden.getEstado().toString(),
            orden.getFechaCreacion(),
            orden.getFechaActualizacion(),
            orden.getItems().stream()
                .map(this::convertirAItemOrdenResponse)
                .collect(Collectors.toList())
        );
    }
    
    private com.tuempresa.ordenes.application.dto.ItemOrdenData convertirAItemOrdenResponse(ItemOrden item) {
        return new com.tuempresa.ordenes.application.dto.ItemOrdenData(
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