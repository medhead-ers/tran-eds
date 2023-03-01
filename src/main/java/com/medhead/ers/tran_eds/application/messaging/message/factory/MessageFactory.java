package com.medhead.ers.tran_eds.application.messaging.message.factory;


import com.medhead.ers.tran_eds.application.messaging.message.EmergencyDispatchedMessage;
import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.dto.Hospital;

public interface MessageFactory {
    static EmergencyDispatchedMessage createEmergencyDispatchedMessage(Emergency emergency, Hospital hospital) {
        return new EmergencyDispatchedMessage(emergency, hospital);
    }
}
