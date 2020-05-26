package com.spring.ticketservice.service;

import com.spring.ticketservice.dto.TicketResource;
import com.spring.ticketservice.model.PriorityType;
import com.spring.ticketservice.model.TicketEntity;
import com.spring.ticketservice.model.TicketStatus;
import com.spring.ticketservice.model.elastic.TicketEntityEs;
import com.spring.ticketservice.repo.TicketRepository;
import com.spring.ticketservice.repo.elastic.TicketRepositoryEs;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketRepositoryEs ticketRepositoryEs;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketRepositoryEs ticketRepositoryEs, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketRepositoryEs = ticketRepositoryEs;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TicketResource save(TicketResource ticketDto) {
        // Ticket Entity for MySQL
        TicketEntity ticket = new TicketEntity();
        if (Objects.isNull(ticketDto.getDescription())){
            throw new IllegalArgumentException("Description cannot be null");
        }

        ticket.setDescription(ticketDto.getDescription());
        ticket.setNotes(ticketDto.getNotes());
        ticket.setTicketDate(ticketDto.getTicketDate());
        ticket.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
        ticket.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));

        // save MySQL
        ticket = ticketRepository.save(ticket);

        // Ticket Entity for ElasticSearch
        TicketEntityEs model = TicketEntityEs.builder()
                .description(ticket.getDescription())
                .notes(ticket.getNotes())
                .id(ticket.getId())
                .priorityType(ticket.getPriorityType().toString())
                .ticketStatus(ticket.getTicketStatus().toString())
                .ticketDate(ticket.getTicketDate()).build();

        // save ElasticSearch
        ticketRepositoryEs.save(model);

        // return dto object
        ticketDto.setId(ticket.getId());
        return ticketDto;
    }

    @Override
    @Transactional
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
