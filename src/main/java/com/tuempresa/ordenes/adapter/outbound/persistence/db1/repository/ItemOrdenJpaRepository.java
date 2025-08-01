package com.tuempresa.ordenes.adapter.outbound.persistence.db1.repository;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.ItemOrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemOrdenJpaRepository extends JpaRepository<ItemOrdenEntity, UUID> {
    
    List<ItemOrdenEntity> findByOrdenId(UUID ordenId);
    
    void deleteByOrdenId(UUID ordenId);
} 