package com.tuempresa.ordenes.application.usecase;

import com.tuempresa.ordenes.application.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.application.dto.OrdenResponse;
import com.tuempresa.ordenes.application.port.out.OrdenRepository;
import com.tuempresa.ordenes.application.port.out.EventPublisher;
import com.tuempresa.ordenes.domain.model.Orden;
import com.tuempresa.ordenes.domain.model.OrdenEstado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CrearOrdenUseCaseImplTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CrearOrdenUseCaseImpl crearOrdenUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearOrden() {
        CrearOrdenRequest request = new CrearOrdenRequest(
                "cliente-1",
                "Cliente Test",
                "cliente@test.com",
                java.util.List.of()
        );
        Orden orden = Orden.builder()
                .id(UUID.randomUUID())
                .numeroOrden("ORD-123")
                .clienteId("cliente-1")
                .clienteNombre("Cliente Test")
                .clienteEmail("cliente@test.com")
                .total(BigDecimal.ZERO)
                .estado(OrdenEstado.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .items(java.util.List.of())
                .build();
        when(ordenRepository.guardar(any(Orden.class))).thenReturn(orden);
        OrdenResponse response = crearOrdenUseCase.crearOrden(request);
        assertThat(response).isNotNull();
        assertThat(response.estado()).isEqualTo("PENDIENTE");
    }
} 