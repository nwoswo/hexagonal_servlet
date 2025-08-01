package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.application.dto.OrdenResponse;
import com.tuempresa.ordenes.application.port.in.CrearOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.event.OrdenCreadaEvent;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.service.OrdenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearOrdenUseCaseImpl implements CrearOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    private final EventPublisher eventPublisher;
    private final OrdenDomainService ordenDomainService;
    
    @Override
    public OrdenResponse crearOrden(CrearOrdenRequest request) {
        // Crear la orden
        Orden orden = new Orden(
            request.getClienteId(),
            request.getClienteNombre(),
            request.getClienteEmail()
        );
        
        // Validar la orden
        ordenDomainService.validarOrden(orden);
        
        // Agregar items a la orden
        if (request.getItems() != null) {
            for (var itemRequest : request.getItems()) {
                ItemOrden item = new ItemOrden(
                    orden.getId(),
                    itemRequest.getProductoId(),
                    itemRequest.getProductoNombre(),
                    itemRequest.getProductoDescripcion(),
                    itemRequest.getPrecioUnitario(),
                    itemRequest.getCantidad()
                );
                
                ordenDomainService.validarItemOrden(item);
                orden.agregarItem(item);
            }
        }
        
        // Guardar la orden
        Orden ordenGuardada = ordenRepository.guardar(orden);
        
        // Publicar evento
        eventPublisher.publicarEvento(new OrdenCreadaEvent(ordenGuardada));
        
        // Convertir a DTO de respuesta
        return convertirAOrdenResponse(ordenGuardada);
    }
    
    private OrdenResponse convertirAOrdenResponse(Orden orden) {
        return OrdenResponse.builder()
                .id(orden.getId())
                .numeroOrden(orden.getNumeroOrden())
                .clienteId(orden.getClienteId())
                .clienteNombre(orden.getClienteNombre())
                .clienteEmail(orden.getClienteEmail())
                .total(orden.getTotal())
                .estado(orden.getEstado())
                .fechaCreacion(orden.getFechaCreacion())
                .fechaActualizacion(orden.getFechaActualizacion())
                .items(orden.getItems().stream()
                        .map(this::convertirAItemOrdenResponse)
                        .toList())
                .build();
    }
    
    private com.tuempresa.ordenes.application.dto.ItemOrdenResponse convertirAItemOrdenResponse(ItemOrden item) {
        return com.tuempresa.ordenes.application.dto.ItemOrdenResponse.builder()
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