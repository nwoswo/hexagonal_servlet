package com.tuempresa.ordenes.domain.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class OrdenEvent {
    
    private UUID eventId;
    private LocalDateTime timestamp;
    private String eventType;
    
    public OrdenEvent(String eventType) {
        this.eventId = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
        this.eventType = eventType;
    }
} 