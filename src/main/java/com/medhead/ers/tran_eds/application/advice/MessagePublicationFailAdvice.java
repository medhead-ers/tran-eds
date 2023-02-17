package com.medhead.ers.tran_eds.application.advice;

import com.medhead.ers.tran_eds.application.messaging.exception.MessagePublicationFailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MessagePublicationFailAdvice {
    @ResponseBody
    @ExceptionHandler(MessagePublicationFailException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String messagePublicationFailAdviceHandler(MessagePublicationFailException exception) {
        return "Message have failed to be published. Error : " + exception.getMessage();
    }
}
