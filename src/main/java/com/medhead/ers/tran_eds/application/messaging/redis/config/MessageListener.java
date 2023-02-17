package com.medhead.ers.tran_eds.application.messaging.redis.config;

import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.application.messaging.exception.CannotCreateEventFromJSONMessageException;
import com.medhead.ers.tran_eds.application.messaging.exception.CannotProcessJobException;
import com.medhead.ers.tran_eds.application.messaging.job.Job;
import com.medhead.ers.tran_eds.application.messaging.service.definition.JobMapper;
import com.medhead.ers.tran_eds.application.messaging.service.definition.MessageToEventConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@AllArgsConstructor
public class MessageListener {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private MessageToEventConverter messageToEventConverter;

    public void receiveMessage(String message) throws CannotCreateEventFromJSONMessageException, CannotProcessJobException {
        Event event = createEventFromMessage(message);
        try {
            Job job = jobMapper.createJobFromEvent(event);
            job.process();
        } catch (Exception exception) {
            throw new CannotProcessJobException(exception);
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
