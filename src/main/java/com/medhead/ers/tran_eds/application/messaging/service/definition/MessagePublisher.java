package com.medhead.ers.tran_eds.application.messaging.service.definition;

import com.medhead.ers.tran_eds.application.messaging.exception.MessagePublicationFailException;
import com.medhead.ers.tran_eds.application.messaging.message.Message;

public interface MessagePublisher {
    public void publish(Message message) throws MessagePublicationFailException;
}
