package com.tuempresa.ordenes.adapter.outbound.rest.client;

import com.tuempresa.ordenes.application.port.out.ExternalServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExternalServiceClientImpl implements ExternalServiceClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${external.services.inventory.url}")
    private String inventoryServiceUrl;
    
    @Value("${external.services.payment.url}")
    private String paymentServiceUrl;
    
    @Override
    public Map<String, Object> consultarInventario(String productoId) {
        try {
            String url = inventoryServiceUrl + "/" + productoId;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            log.info("Consulta de inventario exitosa para producto: {}", productoId);
            return response.getBody() != null ? response.getBody() : new HashMap<>();
            
        } catch (Exception e) {
            log.error("Error al consultar inventario para producto {}: {}", productoId, e.getMessage());
            // Simular respuesta fake para desarrollo
            return crearRespuestaFakeInventario(productoId);
        }
    }
    
    @Override
    public Map<String, Object> procesarPago(String ordenId, String clienteId, Double monto) {
        try {
            String url = paymentServiceUrl + "/procesar";
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("ordenId", ordenId);
            requestBody.put("clienteId", clienteId);
            requestBody.put("monto", monto);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            
            log.info("Procesamiento de pago exitoso para orden: {}", ordenId);
            return response.getBody() != null ? response.getBody() : new HashMap<>();
            
        } catch (Exception e) {
            log.error("Error al procesar pago para orden {}: {}", ordenId, e.getMessage());
            // Simular respuesta fake para desarrollo
            return crearRespuestaFakePago(ordenId);
        }
    }
    
    @Override
    public Map<String, Object> validarCliente(String clienteId) {
        try {
            String url = paymentServiceUrl + "/clientes/" + clienteId + "/validar";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            log.info("Validación de cliente exitosa: {}", clienteId);
            return response.getBody() != null ? response.getBody() : new HashMap<>();
            
        } catch (Exception e) {
            log.error("Error al validar cliente {}: {}", clienteId, e.getMessage());
            // Simular respuesta fake para desarrollo
            return crearRespuestaFakeCliente(clienteId);
        }
    }
    
    private Map<String, Object> crearRespuestaFakeInventario(String productoId) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("productoId", productoId);
        respuesta.put("disponible", true);
        respuesta.put("stock", 100);
        respuesta.put("precio", 29.99);
        return respuesta;
    }
    
    private Map<String, Object> crearRespuestaFakePago(String ordenId) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("ordenId", ordenId);
        respuesta.put("estado", "PROCESADO");
        respuesta.put("transaccionId", "TXN-" + System.currentTimeMillis());
        respuesta.put("fecha", java.time.LocalDateTime.now().toString());
        return respuesta;
    }
    
    private Map<String, Object> crearRespuestaFakeCliente(String clienteId) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("clienteId", clienteId);
        respuesta.put("valido", true);
        respuesta.put("creditoDisponible", 1000.0);
        respuesta.put("estado", "ACTIVO");
        return respuesta;
    }
} 