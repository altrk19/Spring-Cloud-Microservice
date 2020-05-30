package com.microservice.ticketservice.service;

import com.microservice.messaging.TicketNotification;
import com.microservice.ticketservice.model.TicketEntity;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@EnableBinding(Source.class)
public class TicketNotificationServiceImpl implements TicketNotificationService {
    private final Source source;

    public TicketNotificationServiceImpl(Source source) {
        this.source = source;
    }

    @Override
    public void sendToQueue(TicketEntity ticketEntity) {
        TicketNotification ticketNotification =new TicketNotification();
        ticketNotification.setAccountId(ticketEntity.getAssignee());
        ticketNotification.setTicketId(ticketEntity.getId());
        ticketNotification.setTicketDescription(ticketEntity.getDescription());

        source.output().send(MessageBuilder.withPayload(ticketNotification).build());
    }
}
