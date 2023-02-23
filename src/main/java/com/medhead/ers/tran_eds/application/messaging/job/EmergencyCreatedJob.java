package com.medhead.ers.tran_eds.application.messaging.job;

import com.medhead.ers.tran_eds.application.messaging.dto.EmergencyDTO;
import com.medhead.ers.tran_eds.application.messaging.event.Event;

import java.util.LinkedHashMap;

public class EmergencyCreatedJob extends Job{
    public EmergencyCreatedJob(Event event) {
        super(event);
    }

    @Override
    public void process() throws Exception {
        EmergencyDTO emergencyDTO = EmergencyDTO.mapFromEmergencyCreatedEvent(event);
        double lat = emergencyDTO.getGpsCoordinates().getLatitude();
        event.getEventType();
    }
}
