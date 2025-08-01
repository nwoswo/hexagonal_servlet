package com.tuempresa.ordenes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orden {
    
    private UUID id;
    private String numeroOrden;
    private String clienteId;
    private String clienteNombre;
    private String clienteEmail;
    private BigDecimal total;
    private OrdenEstado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<ItemOrden> items;
    
    public Orden(String clienteId, String clienteNombre, String clienteEmail) {
        this.id = UUID.randomUUID();
        this.numeroOrden = generarNumeroOrden();
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.estado = OrdenEstado.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }
    
    private String generarNumeroOrden() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    public void agregarItem(ItemOrden item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        calcularTotal();
    }
    
    public void removerItem(UUID itemId) {
        if (items != null) {
            items.removeIf(item -> item.getId().equals(itemId));
            calcularTotal();
        }
    }
    
    public void calcularTotal() {
        this.total = items.stream()
                .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void confirmar() {
        if (this.estado == OrdenEstado.PENDIENTE) {
            this.estado = OrdenEstado.CONFIRMADA;
            this.fechaActualizacion = LocalDateTime.now();
        }
    }
    
    public void cancelar() {
        if (this.estado != OrdenEstado.CANCELADA) {
            this.estado = OrdenEstado.CANCELADA;
            this.fechaActualizacion = LocalDateTime.now();
        }
    }
    
    public boolean puedeSerCancelada() {
        return this.estado == OrdenEstado.PENDIENTE || this.estado == OrdenEstado.CONFIRMADA;
    }
    
    public boolean tieneItems() {
        return items != null && !items.isEmpty();
    }
} 