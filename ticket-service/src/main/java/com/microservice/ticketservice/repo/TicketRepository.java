package com.microservice.ticketservice.repo;

import com.microservice.ticketservice.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, String> {
}
