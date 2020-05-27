package com.microservice.ticketservice.repo.elastic;

import com.microservice.ticketservice.model.elastic.TicketEntityEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TicketRepositoryEs extends ElasticsearchRepository<TicketEntityEs, String> {
}
