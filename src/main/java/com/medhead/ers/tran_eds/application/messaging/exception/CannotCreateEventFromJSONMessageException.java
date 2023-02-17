package com.medhead.ers.tran_eds.application.messaging.exception;

public class CannotCreateEventFromJSONMessageException extends Exception{
    public CannotCreateEventFromJSONMessageException(String message, Exception exception) {
        super("MessageListener cannot create Event from malformed JSON String : " + message + ". Cause :" + exception.getMessage());
    }
}
