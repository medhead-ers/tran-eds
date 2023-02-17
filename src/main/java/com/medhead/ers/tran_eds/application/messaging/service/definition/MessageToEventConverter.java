package com.medhead.ers.tran_eds.application.messaging.service.definition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.medhead.ers.tran_eds.application.messaging.event.Event;

public interface MessageToEventConverter {
    Event convertMessageToEvent(String message) throws JsonProcessingException;
}
