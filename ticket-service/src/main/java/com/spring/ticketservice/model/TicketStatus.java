package com.spring.ticketservice.model;

import lombok.Getter;

@Getter
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}