package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.application.dto.OrdenResponse;
import com.tuempresa.ordenes.application.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.application.port.in.CrearOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.event.OrdenCreadaEvent;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.model.OrdenEstado;
import com.tuempresa.ordenes.domain.service.OrdenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrearOrdenUseCaseImpl implements CrearOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final EventPublisher eventPublisher;
    private final OrdenDomainService ordenDomainService;
    
    @Override
    public OrdenResponse crearOrden(CrearOrdenRequest request) {
        ordenDomainService.validarOrden(null); // Validación básica
        
        var orden = new Orden(
            UUID.randomUUID(),
            generarNumeroOrden(),
            request.clienteId(),
            request.clienteNombre(),
            request.clienteEmail(),
            BigDecimal.ZERO,
            OrdenEstado.PENDIENTE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            new ArrayList<>()
        );
        
        if (request.items() != null) {
            for (var itemRequest : request.items()) {
                var item = new ItemOrden(
                    UUID.randomUUID(),
                    orden.getId(),
                    itemRequest.productoId(),
                    itemRequest.productoNombre(),
                    itemRequest.productoDescripcion(),
                    BigDecimal.valueOf(itemRequest.precioUnitario()),
                    itemRequest.cantidad(),
                    BigDecimal.ZERO,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                );
                
                ordenDomainService.agregarItemAOrden(orden, item);
            }
        }
        
        orden.calcularTotal();
        ordenRepository.guardar(orden);
        
        eventPublisher.publicarEvento(new OrdenCreadaEvent(orden));
        
        return new OrdenResponse(
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
    
    private String generarNumeroOrden() {
        return "ORD-" + System.currentTimeMillis();
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