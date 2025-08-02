package com.tuempresa.ordenes.adapter.inbound.rest.controller;

import com.tuempresa.ordenes.adapter.inbound.rest.dto.CancelarOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.OrdenResponse;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.ItemOrdenResponse;
import com.tuempresa.ordenes.adapter.inbound.rest.mapper.OrdenMapper;
import com.tuempresa.ordenes.application.port.in.AgregarItemOrdenUseCase;
import com.tuempresa.ordenes.application.port.in.CancelarOrdenUseCase;
import com.tuempresa.ordenes.application.port.in.CrearOrdenUseCase;
import com.tuempresa.ordenes.application.port.in.ObtenerOrdenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {
    
    private final CrearOrdenUseCase crearOrdenUseCase;
    private final ObtenerOrdenUseCase obtenerOrdenUseCase;
    private final CancelarOrdenUseCase cancelarOrdenUseCase;
    private final AgregarItemOrdenUseCase agregarItemOrdenUseCase;
    private final OrdenMapper ordenMapper;
    
    @PostMapping
    public ResponseEntity<OrdenResponse> crearOrden(@Valid @RequestBody CrearOrdenRequest request) {
        var applicationRequest = ordenMapper.toApplicationRequest(request);
        var applicationResponse = crearOrdenUseCase.crearOrden(applicationRequest);
        var response = ordenMapper.toRestResponse(applicationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{ordenId}")
    public ResponseEntity<OrdenResponse> obtenerOrdenPorId(@PathVariable UUID ordenId) {
        var applicationResponse = obtenerOrdenUseCase.obtenerOrdenPorId(ordenId);
        var response = ordenMapper.toRestResponse(applicationResponse);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/numero/{numeroOrden}")
    public ResponseEntity<OrdenResponse> obtenerOrdenPorNumero(@PathVariable String numeroOrden) {
        var applicationResponse = obtenerOrdenUseCase.obtenerOrdenPorNumero(numeroOrden);
        var response = ordenMapper.toRestResponse(applicationResponse);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<OrdenResponse>> obtenerTodasLasOrdenes() {
        var applicationResponses = obtenerOrdenUseCase.obtenerTodasLasOrdenes();
        var responses = ordenMapper.toRestResponseListOrdenes(applicationResponses);
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<OrdenResponse>> obtenerOrdenesPorCliente(@PathVariable String clienteId) {
        var applicationResponses = obtenerOrdenUseCase.obtenerOrdenesPorCliente(clienteId);
        var responses = ordenMapper.toRestResponseListOrdenes(applicationResponses);
        return ResponseEntity.ok(responses);
    }
    
    @PostMapping("/{ordenId}/cancelar")
    public ResponseEntity<Void> cancelarOrden(@PathVariable UUID ordenId, 
                                           @Valid @RequestBody CancelarOrdenRequest request) {
        cancelarOrdenUseCase.cancelarOrden(ordenId, request.motivo());
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{ordenId}/items")
    public ResponseEntity<ItemOrdenResponse> agregarItemAOrden(
            @PathVariable UUID ordenId,
            @Valid @RequestBody CrearItemOrdenRequest request) {
        var applicationRequest = ordenMapper.toApplicationRequest(request);
        var applicationResponse = agregarItemOrdenUseCase.agregarItemAOrden(ordenId, applicationRequest);
        var response = ordenMapper.toRestResponse(applicationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
} 