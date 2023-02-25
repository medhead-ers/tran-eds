package com.medhead.ers.tran_eds.application.messaging.job;

import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.domain.converter.EmergencyConverter;
import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.service.definition.EmergencyDispatcher;

public class EmergencyCreatedJob extends Job{
    EmergencyDispatcher emergencyDispatcher;
    public EmergencyCreatedJob(Event event) {
        super(event);
    }

    @Override
    public void process() throws Exception {
        emergencyDispatcher.dispatchEmergency(
                EmergencyConverter.convertFromEmergencyCreatedEvent(event)
        );
    }
}
