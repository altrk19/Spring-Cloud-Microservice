package com.microservice.ticketservice.controller;

import com.microservice.ticketservice.dto.TicketResource;
import com.microservice.ticketservice.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ticket")
@RestController
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketResource>> getAllTickets(Pageable pageable) {
        return ResponseEntity.ok(ticketService.getAllAccounts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResource> getSingleTicket(@PathVariable String id) {
        return ResponseEntity.ok(ticketService.getSingleTicket(id));
    }

    @PostMapping
    public ResponseEntity<TicketResource> createTicket(@RequestBody TicketResource ticketDto) {
        return ResponseEntity.ok(ticketService.createTicket(ticketDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResource> updateTicket(@PathVariable String id,
                                                       @RequestBody TicketResource ticketDto) {
        return ResponseEntity.ok(ticketService.updateTicket(id, ticketDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}