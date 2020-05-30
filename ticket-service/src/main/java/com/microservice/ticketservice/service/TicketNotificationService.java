package com.microservice.ticketservice.service;

import com.microservice.ticketservice.model.TicketEntity;

public interface TicketNotificationService {
    void sendToQueue(TicketEntity ticketEntity);
}
