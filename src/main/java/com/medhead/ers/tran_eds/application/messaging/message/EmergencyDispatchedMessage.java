package com.medhead.ers.tran_eds.application.messaging.message;

import com.medhead.ers.tran_eds.application.messaging.event.AvailableEvent;
import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.dto.Hospital;
import org.json.JSONObject;

public class EmergencyDispatchedMessage extends Message{
    public EmergencyDispatchedMessage(Emergency emergency, Hospital hospital){
        this.eventType = AvailableEvent.EmergencyDispatched;
        this.setMetadata(new JSONObject()
                .put("patientId", emergency.getPatientId())
                .put("emergencyId", emergency.getId())
                .put("hospitalId", hospital.getId())
                .toMap()
        );
    }
}
