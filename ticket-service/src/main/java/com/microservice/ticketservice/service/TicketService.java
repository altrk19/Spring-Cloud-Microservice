package com.microservice.ticketservice.service;

import com.microservice.ticketservice.dto.TicketResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    Page<TicketResource> getAllAccounts(Pageable pageable);
    TicketResource getSingleTicket(String ticketId);
    TicketResource createTicket(TicketResource ticketDto);
    TicketResource updateTicket(String id, TicketResource ticketDto);
    void deleteTicket(String ticketId);
}