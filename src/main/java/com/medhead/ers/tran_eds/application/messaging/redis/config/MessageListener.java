package com.medhead.ers.tran_eds.application.messaging.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.application.messaging.exception.CannotCreateEventFromJSONMessageException;
import com.medhead.ers.tran_eds.application.messaging.exception.CannotProcessJobException;
import com.medhead.ers.tran_eds.application.messaging.job.Job;
import com.medhead.ers.tran_eds.application.messaging.service.definition.JobMapper;
import com.medhead.ers.tran_eds.application.messaging.service.definition.MessageToEventConverter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;

@NoArgsConstructor
public class MessageListener {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private MessageToEventConverter messageToEventConverter;
    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public MessageListener(JobMapper jobMapper, MessageToEventConverter messageToEventConverter){
        this.jobMapper = jobMapper;
        this.messageToEventConverter = messageToEventConverter;
    }

    public void receiveMessage(String message) throws CannotProcessJobException {
        Event event = null;
        try {
            event = createEventFromMessage(message);
        } catch (CannotCreateEventFromJSONMessageException e) {
            try {
                HashMap<String, Object> receivedMessage = (HashMap<String, Object>) new ObjectMapper().readValue(message, Object.class);
                String eventType = (String) receivedMessage.get("eventType");
                if (eventType == null) {
                    throw new IOException();
                }
                logger.info("Message reçu de type inconnu ("+eventType+") - Pas d'événement éligible associé. Le message sera ignoré.");
                return;
            } catch (Exception ex) {
                logger.info("Message malformé reçu. Le message sera ignoré.");
                return;
            }
        }

        logger.info("Message reçu de type : " + event.getEventType().toString());

        if(jobMapper.checkIfJobExistForEvent(event)) {
            try {
                Job job = jobMapper.createJobFromEvent(event);
                logger.info("Traitement de l'événement de type : " + event.getEventType().toString() +". Job processor : "+ job.getClass().getSimpleName());
                job.process();
            } catch (Exception exception) {
                throw new CannotProcessJobException(exception);
            }
        }
        else {
            logger.info("Aucun job processor trouvé pour l'événement de type : " + event.getEventType().toString() + " - Message ignoré.");
        }
    }

    private Event createEventFromMessage(String message) throws CannotCreateEventFromJSONMessageException {
        try {
            return messageToEventConverter.convertMessageToEvent(message);
        } catch (Exception exception) {
            throw new CannotCreateEventFromJSONMessageException(message, exception);
        }
    }

}