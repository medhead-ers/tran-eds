package com.medhead.ers.tran_eds.application.messaging.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.application.messaging.service.definition.MessageToEventConverter;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageToEventConverter implements MessageToEventConverter {
    @Override
    public Event convertMessageToEvent(String message) throws JsonProcessingException, InvalidFormatException {
        return new ObjectMapper().readValue(message, Event.class);
    }
}
