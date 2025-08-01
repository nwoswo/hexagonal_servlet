package com.tuempresa.ordenes.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Orden Domain Model Tests")
class OrdenTest {

    private Orden orden;
    private ItemOrden item1;
    private ItemOrden item2;

    @BeforeEach
    void setUp() {
        orden = new Orden(
            UUID.randomUUID(),
            "ORD-123456789",
            "CLI-001",
            "Juan Pérez",
            "juan@example.com",
            BigDecimal.ZERO,
            OrdenEstado.PENDIENTE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            null
        );

        item1 = new ItemOrden(
            UUID.randomUUID(),
            orden.getId(),
            "PROD-001",
            "Laptop Gaming",
            "Laptop de alto rendimiento",
            BigDecimal.valueOf(1500.00),
            1,
            BigDecimal.valueOf(1500.00),
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        item2 = new ItemOrden(
            UUID.randomUUID(),
            orden.getId(),
            "PROD-002",
            "Mouse Gaming",
            "Mouse de alta precisión",
            BigDecimal.valueOf(50.00),
            2,
            BigDecimal.valueOf(100.00),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("Debería crear una orden con estado PENDIENTE")
    void deberiaCrearOrdenConEstadoPendiente() {
        // Given & When
        Orden nuevaOrden = new Orden("CLI-001", "Juan Pérez", "juan@example.com");

        // Then
        assertThat(nuevaOrden.getEstado()).isEqualTo(OrdenEstado.PENDIENTE);
        assertThat(nuevaOrden.getClienteId()).isEqualTo("CLI-001");
        assertThat(nuevaOrden.getClienteNombre()).isEqualTo("Juan Pérez");
        assertThat(nuevaOrden.getClienteEmail()).isEqualTo("juan@example.com");
        assertThat(nuevaOrden.getTotal()).isEqualTo(BigDecimal.ZERO);
        assertThat(nuevaOrden.getItems()).isEmpty();
    }

    @Test
    @DisplayName("Debería agregar item a la orden")
    void deberiaAgregarItemAOrden() {
        // When
        orden.agregarItem(item1);

        // Then
        assertThat(orden.getItems()).hasSize(1);
        assertThat(orden.getItems()).contains(item1);
        assertThat(orden.getTotal()).isEqualTo(BigDecimal.valueOf(1500.00));
    }

    @Test
    @DisplayName("Debería calcular total correctamente con múltiples items")
    void deberiaCalcularTotalConMultiplesItems() {
        // Given
        orden.agregarItem(item1);
        orden.agregarItem(item2);

        // When
        orden.calcularTotal();

        // Then
        BigDecimal totalEsperado = BigDecimal.valueOf(1600.00); // 1500 + 100
        assertThat(orden.getTotal()).isEqualTo(totalEsperado);
        assertThat(orden.getItems()).hasSize(2);
    }

    @Test
    @DisplayName("Debería remover item de la orden")
    void deberiaRemoverItemDeOrden() {
        // Given
        orden.agregarItem(item1);
        orden.agregarItem(item2);

        // When
        orden.removerItem(item1.getId());

        // Then
        assertThat(orden.getItems()).hasSize(1);
        assertThat(orden.getItems()).contains(item2);
        assertThat(orden.getItems()).doesNotContain(item1);
        assertThat(orden.getTotal()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    @DisplayName("Debería confirmar orden desde estado PENDIENTE")
    void deberiaConfirmarOrdenDesdePendiente() {
        // Given
        assertThat(orden.getEstado()).isEqualTo(OrdenEstado.PENDIENTE);

        // When
        orden.confirmar();

        // Then
        assertThat(orden.getEstado()).isEqualTo(OrdenEstado.CONFIRMADA);
    }

    @Test
    @DisplayName("Debería cancelar orden")
    void deberiaCancelarOrden() {
        // Given
        assertThat(orden.getEstado()).isEqualTo(OrdenEstado.PENDIENTE);

        // When
        orden.cancelar();

        // Then
        assertThat(orden.getEstado()).isEqualTo(OrdenEstado.CANCELADA);
    }

    @Test
    @DisplayName("Debería verificar si orden puede ser cancelada")
    void deberiaVerificarSiOrdenPuedeSerCancelada() {
        // Given & When & Then
        assertThat(orden.puedeSerCancelada()).isTrue(); // PENDIENTE

        orden.confirmar();
        assertThat(orden.puedeSerCancelada()).isTrue(); // CONFIRMADA

        orden.cancelar();
        assertThat(orden.puedeSerCancelada()).isFalse(); // CANCELADA
    }

    @Test
    @DisplayName("Debería verificar si orden tiene items")
    void deberiaVerificarSiOrdenTieneItems() {
        // Given & When & Then
        assertThat(orden.tieneItems()).isFalse();

        orden.agregarItem(item1);
        assertThat(orden.tieneItems()).isTrue();
    }

    @Test
    @DisplayName("Debería generar número de orden único")
    void deberiaGenerarNumeroOrdenUnico() {
        // When
        Orden orden1 = new Orden("CLI-001", "Juan", "juan@example.com");
        Orden orden2 = new Orden("CLI-002", "María", "maria@example.com");

        // Then
        assertThat(orden1.getNumeroOrden()).isNotEqualTo(orden2.getNumeroOrden());
        assertThat(orden1.getNumeroOrden()).startsWith("ORD-");
        assertThat(orden2.getNumeroOrden()).startsWith("ORD-");
    }

    @Test
    @DisplayName("Debería actualizar fecha de actualización al modificar")
    void deberiaActualizarFechaAlModificar() {
        // Given
        LocalDateTime fechaOriginal = orden.getFechaActualizacion();

        // When
        try {
            Thread.sleep(10); // Pequeña pausa para asegurar diferencia de tiempo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        orden.confirmar();

        // Then
        assertThat(orden.getFechaActualizacion()).isAfter(fechaOriginal);
    }
} 