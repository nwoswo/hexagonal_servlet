package com.tuempresa.ordenes.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CrearOrdenRequest(
    @NotBlank(message = "El ID del cliente es requerido") String clienteId,
    @NotBlank(message = "El nombre del cliente es requerido") String clienteNombre,
    @NotBlank(message = "El email del cliente es requerido") 
    @Email(message = "El formato del email no es válido") String clienteEmail,
    @NotEmpty(message = "La lista de items es requerida") 
    @Size(min = 1, message = "Debe tener al menos un item") List<CrearItemOrdenRequest> items
) {} 