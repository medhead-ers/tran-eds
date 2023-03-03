package com.medhead.ers.tran_eds.domain.service.definition;

import com.medhead.ers.tran_eds.application.messaging.exception.MessagePublicationFailException;
import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.dto.Hospital;

import java.io.IOException;

public interface EmergencyDispatcher {
    Hospital dispatchEmergency(Emergency emergency) throws IOException, MessagePublicationFailException;
}
