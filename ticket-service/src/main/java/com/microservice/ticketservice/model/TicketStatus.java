package com.microservice.ticketservice.model;

import lombok.Getter;

@Getter
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}