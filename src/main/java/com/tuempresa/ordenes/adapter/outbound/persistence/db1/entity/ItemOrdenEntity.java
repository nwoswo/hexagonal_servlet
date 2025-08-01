package com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "items_orden")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrdenEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "orden_id", nullable = false)
    private UUID ordenId;
    
    @Column(name = "producto_id", nullable = false)
    private String productoId;
    
    @Column(name = "producto_nombre", nullable = false)
    private String productoNombre;
    
    @Column(name = "producto_descripcion")
    private String productoDescripcion;
    
    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;
    
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
} 