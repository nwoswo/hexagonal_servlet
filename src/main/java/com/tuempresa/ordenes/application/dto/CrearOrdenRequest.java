package com.tuempresa.ordenes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearOrdenRequest {
    
    @NotBlank(message = "El ID del cliente es requerido")
    private String clienteId;
    
    @NotBlank(message = "El nombre del cliente es requerido")
    private String clienteNombre;
    
    @NotBlank(message = "El email del cliente es requerido")
    @Email(message = "El formato del email no es válido")
    private String clienteEmail;
    
    @NotNull(message = "La lista de items es requerida")
    private List<CrearItemOrdenRequest> items;
} 