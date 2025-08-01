package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.OrdenResponse;
import com.tuempresa.ordenes.application.port.in.ObtenerOrdenUseCase;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.exception.OrdenNoEncontradaException;
import com.tuempresa.ordenes.domain.model.Orden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObtenerOrdenUseCaseImpl implements ObtenerOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    
    @Override
    public OrdenResponse obtenerOrdenPorId(UUID ordenId) {
        Orden orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
        return convertirAOrdenResponse(orden);
    }
    
    @Override
    public OrdenResponse obtenerOrdenPorNumero(String numeroOrden) {
        Orden orden = ordenRepository.buscarPorNumero(numeroOrden)
                .orElseThrow(() -> new OrdenNoEncontradaException(numeroOrden));
        
        return convertirAOrdenResponse(orden);
    }
    
    @Override
    public List<OrdenResponse> obtenerTodasLasOrdenes() {
        return ordenRepository.buscarTodas().stream()
                .map(this::convertirAOrdenResponse)
                .toList();
    }
    
    @Override
    public List<OrdenResponse> obtenerOrdenesPorCliente(String clienteId) {
        return ordenRepository.buscarPorClienteId(clienteId).stream()
                .map(this::convertirAOrdenResponse)
                .toList();
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
    
    private com.tuempresa.ordenes.application.dto.ItemOrdenResponse convertirAItemOrdenResponse(com.tuempresa.ordenes.domain.model.ItemOrden item) {
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