package com.microservice.ticketservice.service;

import com.microservice.ticketservice.dto.TicketResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    TicketResource save(TicketResource ticketDto);
    TicketResource update(String id, TicketResource ticketDto);
    TicketResource getById(String ticketId);
    Page<TicketResource> getPagination(Pageable pageable);
}