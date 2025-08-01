package com.tuempresa.ordenes.adapter.inbound.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CrearItemOrdenRequest;
import com.tuempresa.ordenes.adapter.inbound.rest.dto.CancelarOrdenRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@DisplayName("OrdenController Integration Tests")
class OrdenControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    @DisplayName("Debería crear orden exitosamente")
    void deberiaCrearOrdenExitosamente() throws Exception {
        // Given
        CrearOrdenRequest request = new CrearOrdenRequest(
            "CLI-001",
            "Juan Pérez",
            "juan@example.com",
            List.of(
                new CrearItemOrdenRequest(
                    "PROD-001",
                    "Laptop Gaming",
                    "Laptop de alto rendimiento",
                    1500.00,
                    1
                )
            )
        );

        // When & Then
        mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value("CLI-001"))
                .andExpect(jsonPath("$.clienteNombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.clienteEmail").value("juan@example.com"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.total").value(1500.00))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.numeroOrden").exists());
    }

    @Test
    @DisplayName("Debería obtener todas las órdenes")
    void deberiaObtenerTodasLasOrdenes() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Debería obtener orden por ID")
    void deberiaObtenerOrdenPorId() throws Exception {
        // Given - Primero crear una orden
        CrearOrdenRequest crearRequest = new CrearOrdenRequest(
            "CLI-002",
            "María García",
            "maria@example.com",
            List.of()
        );

        String responseJson = mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraer el ID de la respuesta
        String ordenId = objectMapper.readTree(responseJson).get("id").asText();

        // When & Then - Obtener la orden por ID
        mockMvc.perform(get("/api/ordenes/{id}", ordenId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ordenId))
                .andExpect(jsonPath("$.clienteId").value("CLI-002"))
                .andExpect(jsonPath("$.clienteNombre").value("María García"));
    }

    @Test
    @DisplayName("Debería agregar item a orden")
    void deberiaAgregarItemAOrden() throws Exception {
        // Given - Primero crear una orden
        CrearOrdenRequest crearRequest = new CrearOrdenRequest(
            "CLI-003",
            "Carlos López",
            "carlos@example.com",
            List.of()
        );

        String responseJson = mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String ordenId = objectMapper.readTree(responseJson).get("id").asText();

        // When - Agregar item a la orden
        CrearItemOrdenRequest itemRequest = new CrearItemOrdenRequest(
            "PROD-002",
            "Mouse Gaming",
            "Mouse de alta precisión",
            50.00,
            2
        );

        // Then
        mockMvc.perform(post("/api/ordenes/{id}/items", ordenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productoId").value("PROD-002"))
                .andExpect(jsonPath("$.productoNombre").value("Mouse Gaming"))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(50.00));
    }

    @Test
    @DisplayName("Debería cancelar orden")
    void deberiaCancelarOrden() throws Exception {
        // Given - Primero crear una orden
        CrearOrdenRequest crearRequest = new CrearOrdenRequest(
            "CLI-004",
            "Ana Martínez",
            "ana@example.com",
            List.of()
        );

        String responseJson = mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String ordenId = objectMapper.readTree(responseJson).get("id").asText();

        // When - Cancelar la orden
        CancelarOrdenRequest cancelarRequest = new CancelarOrdenRequest("Motivo de cancelación");

        // Then
        mockMvc.perform(post("/api/ordenes/{id}/cancelar", ordenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cancelarRequest)))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Verificar que la orden está cancelada
        mockMvc.perform(get("/api/ordenes/{id}", ordenId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CANCELADA"));
    }

    @Test
    @DisplayName("Debería retornar 404 para orden inexistente")
    void deberiaRetornar404ParaOrdenInexistente() throws Exception {
        // Given
        String ordenIdInexistente = UUID.randomUUID().toString();

        // When & Then
        mockMvc.perform(get("/api/ordenes/{id}", ordenIdInexistente)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debería validar campos requeridos al crear orden")
    void deberiaValidarCamposRequeridosAlCrearOrden() throws Exception {
        // Given - Request sin campos requeridos
        CrearOrdenRequest requestInvalido = new CrearOrdenRequest(
            null, // clienteId faltante
            "",   // clienteNombre vacío
            "email-invalido", // email inválido
            List.of()
        );

        // When & Then
        mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería validar precio positivo al agregar item")
    void deberiaValidarPrecioPositivoAlAgregarItem() throws Exception {
        // Given - Primero crear una orden
        CrearOrdenRequest crearRequest = new CrearOrdenRequest(
            "CLI-005",
            "Pedro Sánchez",
            "pedro@example.com",
            List.of()
        );

        String responseJson = mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String ordenId = objectMapper.readTree(responseJson).get("id").asText();

        // When - Agregar item con precio negativo
        CrearItemOrdenRequest itemRequestInvalido = new CrearItemOrdenRequest(
            "PROD-003",
            "Producto con precio negativo",
            "Descripción",
            -10.00, // Precio negativo
            1
        );

        // Then
        mockMvc.perform(post("/api/ordenes/{id}/items", ordenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemRequestInvalido)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
} 