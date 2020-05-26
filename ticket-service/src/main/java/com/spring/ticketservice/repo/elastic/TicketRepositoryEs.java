package com.spring.ticketservice.repo.elastic;

import com.spring.ticketservice.model.elastic.TicketEntityEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TicketRepositoryEs extends ElasticsearchRepository<TicketEntityEs, String> {
}
