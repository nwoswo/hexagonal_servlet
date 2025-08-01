package com.tuempresa.ordenes.shared.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
    String message,
    String error,
    int status,
    LocalDateTime timestamp
) {} 