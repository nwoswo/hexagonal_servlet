package com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity;

import com.tuempresa.ordenes.domain.model.enums.OrdenEstado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ordenes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "numero_orden", unique = true, nullable = false)
    private String numeroOrden;
    
    @Column(name = "cliente_id", nullable = false)
    private String clienteId;
    
    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;
    
    @Column(name = "cliente_email", nullable = false)
    private String clienteEmail;
    
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private OrdenEstado estado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
} 