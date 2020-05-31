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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketRepositoryEs ticketRepositoryEs;
    private final AccountServiceClient accountServiceClient;
    private final TicketNotificationService ticketNotificationService;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             TicketRepositoryEs ticketRepositoryEs,
                             AccountServiceClient accountServiceClient,
                             TicketNotificationService ticketNotificationService, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketRepositoryEs = ticketRepositoryEs;
        this.accountServiceClient = accountServiceClient;
        this.ticketNotificationService = ticketNotificationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TicketResource getSingleTicket(String ticketId) {
        Optional<TicketEntityEs> ticketEntityEs = ticketRepositoryEs.findById(ticketId);
        TicketResource ticketResource = ticketEntityEs.map(entityEs -> {
            TicketResource ticketResourceEs = new TicketResource();
            ticketResourceEs.setAssignee(entityEs.getAssignee());
            ticketResourceEs.setDescription(entityEs.getDescription());
            ticketResourceEs.setId(entityEs.getId());
            ticketResourceEs.setNotes(entityEs.getNotes());
            ticketResourceEs.setTicketDate(entityEs.getTicketDate());
            ticketResourceEs.setPriorityType(entityEs.getPriorityType());
            ticketResourceEs.setTicketStatus(entityEs.getTicketStatus());
            return ticketResourceEs;
        }).orElse(null);


        if (Objects.isNull(ticketResource)) {
            Optional<TicketEntity> ticketEntity = ticketRepository.findById(ticketId);
            ticketResource = ticketEntity.map(entity -> {
                TicketResource ticketResourcePersist = new TicketResource();
                ticketResourcePersist.setAssignee(entity.getAssignee());
                ticketResourcePersist.setDescription(entity.getDescription());
                ticketResourcePersist.setId(entity.getId());
                ticketResourcePersist.setNotes(entity.getNotes());
                ticketResourcePersist.setTicketDate(entity.getTicketDate());
                ticketResourcePersist.setPriorityType(entity.getPriorityType().toString());
                ticketResourcePersist.setTicketStatus(entity.getTicketStatus().toString());
                return ticketResourcePersist;
            }).orElseThrow(IllegalArgumentException::new);

            if(Objects.nonNull(ticketResource)){
                ticketRepositoryEs.save(modelMapper.map(ticketEntity, TicketEntityEs.class));
            }
        }

        return ticketResource;
    }

    @Override
    public Page<TicketResource> getAllAccounts(Pageable pageable) {
        Page<TicketEntityEs> ticketEntityEs = ticketRepositoryEs.findAll(pageable);
        return ticketEntityEs.map(entityEs -> {
            TicketResource ticketResource = new TicketResource();
            ticketResource.setId(entityEs.getId());
            ticketResource.setAssignee(entityEs.getAssignee());
            ticketResource.setDescription(entityEs.getDescription());
            ticketResource.setNotes(entityEs.getNotes());
            ticketResource.setTicketDate(entityEs.getTicketDate());
            ticketResource.setPriorityType(entityEs.getPriorityType());
            ticketResource.setTicketStatus(entityEs.getTicketStatus());
            return ticketResource;
        });
    }

    @Override
    @Transactional
    public TicketResource createTicket(TicketResource ticketDto) {
        if (Objects.isNull(ticketDto.getDescription())) {
            throw new IllegalArgumentException("Description cannot be null");
        }

        // Ticket Entity for MySQL
        TicketEntity ticket = new TicketEntity();
        ResponseEntity<AccountResource> accountResourceResponse =
                accountServiceClient.getSingleAccount(ticketDto.getAssignee());

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
                .assignee(ticket.getAssignee())
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
    public TicketResource updateTicket(String id, TicketResource ticketDto) {
        Assert.notNull(id, "id cannot be null");

        ResponseEntity<AccountResource> accountResourceResponse =
                accountServiceClient.getSingleAccount(ticketDto.getAssignee());

        Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);
        TicketEntity ticketToUpdate = ticketEntity.map(entity -> {
            entity.setAssignee(accountResourceResponse.getBody().getId());
            entity.setDescription(ticketDto.getDescription());
            entity.setNotes(ticketDto.getNotes());
            entity.setTicketDate(ticketDto.getTicketDate());
            entity.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));
            entity.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
            return entity;
        }).orElseThrow(IllegalArgumentException::new);
        TicketResource ticketResource = modelMapper.map(ticketRepository.save(ticketToUpdate), TicketResource.class);

        TicketEntityEs ticketEntityEs = modelMapper.map(ticketToUpdate, TicketEntityEs.class);
        ticketRepositoryEs.save(ticketEntityEs);

        return ticketResource;
    }

    @Override
    public void deleteTicket(String ticketId) {
        ticketRepositoryEs.deleteById(ticketId);
        ticketRepository.deleteById(ticketId);
    }
}
