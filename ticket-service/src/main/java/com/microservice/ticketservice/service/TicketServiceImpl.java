package com.microservice.ticketservice.service;

import com.microservice.client.AccountServiceClient;
import com.microservice.client.dto.AccountResource;
import com.microservice.ticketservice.dto.TicketResource;
import com.microservice.ticketservice.model.PriorityType;
import com.microservice.ticketservice.model.TicketEntity;
import com.microservice.ticketservice.model.TicketStatus;
import com.microservice.ticketservice.model.elastic.TicketEntityEs;
import com.microservice.ticketservice.repo.TicketRepository;
import com.microservice.ticketservice.repo.elastic.TicketRepositoryEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketRepositoryEs ticketRepositoryEs;
    private final AccountServiceClient accountServiceClient;
    private final TicketNotificationService ticketNotificationService;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketRepositoryEs ticketRepositoryEs,
                             AccountServiceClient accountServiceClient,
                             TicketNotificationService ticketNotificationService) {
        this.ticketRepository = ticketRepository;
        this.ticketRepositoryEs = ticketRepositoryEs;
        this.accountServiceClient = accountServiceClient;
        this.ticketNotificationService = ticketNotificationService;
    }

    @Override
    @Transactional
    public TicketResource save(TicketResource ticketDto) {
        if (Objects.isNull(ticketDto.getDescription())){
            throw new IllegalArgumentException("Description cannot be null");
        }

        // Ticket Entity for MySQL
        TicketEntity ticket = new TicketEntity();
        ResponseEntity<AccountResource> accountResourceResponse = accountServiceClient.getSingleAccount(ticketDto.getAssignee());

        ticket.setDescription(ticketDto.getDescription());
        ticket.setNotes(ticketDto.getNotes());
        ticket.setTicketDate(ticketDto.getTicketDate());
        ticket.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
        ticket.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));
        ticket.setAssignee(accountResourceResponse.getBody().getId());

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

        ticketDto.setId(ticket.getId());

        //send message to queue
        ticketNotificationService.sendToQueue(ticket);

        // return dto object
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
