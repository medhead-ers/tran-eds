package com.medhead.ers.tran_eds.application.messaging.exception;

public class MessagePublicationFailException extends Exception {
    public MessagePublicationFailException(Exception exception) {
        super(exception.getMessage());
    }
}
