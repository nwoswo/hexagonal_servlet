package com.tuempresa.ordenes.adapter.outbound.persistence.db1.repository;

import com.tuempresa.ordenes.adapter.outbound.persistence.db1.entity.OrdenEntity;
import com.tuempresa.ordenes.adapter.outbound.persistence.db1.mapper.OrdenPersistenceMapper;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.domain.model.Orden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrdenRepositoryImpl implements OrdenRepository {
    
    private final OrdenJpaRepository ordenJpaRepository;
    private final OrdenPersistenceMapper ordenPersistenceMapper;
    
    @Override
    public Orden guardar(Orden orden) {
        OrdenEntity entity = ordenPersistenceMapper.toEntity(orden);
        OrdenEntity savedEntity = ordenJpaRepository.save(entity);
        return ordenPersistenceMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Orden> buscarPorId(UUID ordenId) {
        return ordenJpaRepository.findById(ordenId)
                .map(ordenPersistenceMapper::toDomain);
    }
    
    @Override
    public Optional<Orden> buscarPorNumero(String numeroOrden) {
        return ordenJpaRepository.findByNumeroOrden(numeroOrden)
                .map(ordenPersistenceMapper::toDomain);
    }
    
    @Override
    public List<Orden> buscarTodas() {
        List<OrdenEntity> entities = ordenJpaRepository.findAll();
        return ordenPersistenceMapper.toDomainList(entities);
    }
    
    @Override
    public List<Orden> buscarPorClienteId(String clienteId) {
        List<OrdenEntity> entities = ordenJpaRepository.findByClienteId(clienteId);
        return ordenPersistenceMapper.toDomainList(entities);
    }
    
    @Override
    public void eliminar(UUID ordenId) {
        ordenJpaRepository.deleteById(ordenId);
    }
    
    @Override
    public boolean existePorId(UUID ordenId) {
        return ordenJpaRepository.existsById(ordenId);
    }
} 