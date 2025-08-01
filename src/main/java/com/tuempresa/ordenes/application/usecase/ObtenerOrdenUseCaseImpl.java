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
import java.util.stream.Collectors;
import com.tuempresa.ordenes.application.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.domain.model.ItemOrden;

@Service
@RequiredArgsConstructor
public class ObtenerOrdenUseCaseImpl implements ObtenerOrdenUseCase {
    
    private final OrdenRepository ordenRepository;
    
    @Override
    public OrdenResponse obtenerOrdenPorId(UUID ordenId) {
        var orden = ordenRepository.buscarPorId(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        
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