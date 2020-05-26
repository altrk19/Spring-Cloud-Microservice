package com.spring.ticketservice.service;

import com.spring.ticketservice.dto.TicketResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TicketServiceImpl implements TicketService {
    @Override
    public TicketResource save(TicketResource ticketDto) {
        return null;
    }

    @Override
    public TicketResource update(String id, TicketResource ticketDto) {
        return null;
    }

    @Override
    public TicketResource getById(String ticketId) {
        return null;
    }

    @Override
    public Page<TicketResource> getPagination(Pageable pageable) {
        return null;
    }
}
