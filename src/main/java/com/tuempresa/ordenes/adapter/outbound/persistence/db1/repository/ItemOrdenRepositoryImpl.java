package com.tuempresa.ordenes.adapter.outbound.persistence.db1.repository;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.ItemOrdenEntity;
import com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper.ItemOrdenPersistenceMapper;
import com.tuempresa.ordenes.application.port.out.ItemOrdenRepository;
import com.tuempresa.ordenes.domain.model.ItemOrden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ItemOrdenRepositoryImpl implements ItemOrdenRepository {
    
    private final ItemOrdenJpaRepository itemOrdenJpaRepository;
    private final ItemOrdenPersistenceMapper itemOrdenPersistenceMapper;
    
    @Override
    public ItemOrden guardar(ItemOrden itemOrden) {
        ItemOrdenEntity entity = itemOrdenPersistenceMapper.toEntity(itemOrden);
        ItemOrdenEntity savedEntity = itemOrdenJpaRepository.save(entity);
        return itemOrdenPersistenceMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<ItemOrden> buscarPorId(UUID itemId) {
        return itemOrdenJpaRepository.findById(itemId)
                .map(itemOrdenPersistenceMapper::toDomain);
    }
    
    @Override
    public List<ItemOrden> buscarPorOrdenId(UUID ordenId) {
        List<ItemOrdenEntity> entities = itemOrdenJpaRepository.findByOrdenId(ordenId);
        return itemOrdenPersistenceMapper.toDomainList(entities);
    }
    
    @Override
    public void eliminar(UUID itemId) {
        itemOrdenJpaRepository.deleteById(itemId);
    }
    
    @Override
    public void eliminarPorOrdenId(UUID ordenId) {
        itemOrdenJpaRepository.deleteByOrdenId(ordenId);
    }
    
    @Override
    public boolean existePorId(UUID itemId) {
        return itemOrdenJpaRepository.existsById(itemId);
    }
} 