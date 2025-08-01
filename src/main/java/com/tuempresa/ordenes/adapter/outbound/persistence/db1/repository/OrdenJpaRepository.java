package com.tuempresa.ordenes.adapter.outbound.persistence.db1.repository;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.OrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdenJpaRepository extends JpaRepository<OrdenEntity, UUID> {
    
    Optional<OrdenEntity> findByNumeroOrden(String numeroOrden);
    
    List<OrdenEntity> findByClienteId(String clienteId);
    
    boolean existsByNumeroOrden(String numeroOrden);
} 