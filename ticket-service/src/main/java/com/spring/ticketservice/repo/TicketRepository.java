package com.spring.ticketservice.repo;

import com.spring.ticketservice.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, String> {
}
