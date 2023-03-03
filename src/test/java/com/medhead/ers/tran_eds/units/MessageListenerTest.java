package com.medhead.ers.tran_eds.units;

import com.medhead.ers.tran_eds.application.messaging.exception.CannotProcessJobException;
import com.medhead.ers.tran_eds.application.messaging.redis.config.MessageListener;
import com.medhead.ers.tran_eds.application.messaging.service.implementation.JobMapperImpl;
import com.medhead.ers.tran_eds.application.messaging.service.implementation.RedisMessageToEventConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
public class MessageListenerTest {

    private final MessageListener messageListener = new MessageListener(new JobMapperImpl(), new RedisMessageToEventConverter());
    @Test
    void test_CannotProcessJobBecauseUnknownMessage(CapturedOutput output) throws CannotProcessJobException {
        // Given
        String eventType = "InvalidMessage";
        String invalidMessage = "{\r\n  \"eventType\" : \""+eventType+"\",\r\n  \"metadata\" : {}\r\n}";
        // When
        messageListener.receiveMessage(invalidMessage);
        // Then
        Assertions.assertTrue(output.getAll().contains("Message reçu de type inconnu ("+eventType+") - Pas d'événement éligible associé. Le message sera ignoré."));
    }

    @Test
    void test_CannotProcessJobBecauseMalformedMessage(CapturedOutput output) throws CannotProcessJobException {
        // GIven
        String malformedMessage = "Not A JSON String";
        // When
        messageListener.receiveMessage(malformedMessage);
        // Then
        Assertions.assertTrue(output.getAll().contains("Message malformé reçu. Le message sera ignoré."));
    }

    @Test
    void test_CannotProcessJobBecauseEventTypeIsNullInMessage(CapturedOutput output) throws CannotProcessJobException {
        // GIven
        String eventTypeNullMessage = "{\r\n  \"eventType\" : \"\",\r\n  \"metadata\" : {}\r\n}";
        // When
        messageListener.receiveMessage(eventTypeNullMessage);
        // Then
        Assertions.assertTrue(output.getAll().contains("Message malformé reçu. Le message sera ignoré."));
    }
}
