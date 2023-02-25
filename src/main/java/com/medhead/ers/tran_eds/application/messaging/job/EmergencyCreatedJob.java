package com.medhead.ers.tran_eds.application.messaging.job;

import com.medhead.ers.tran_eds.domain.converter.EmergencyConverter;
import com.medhead.ers.tran_eds.domain.service.definition.EmergencyDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmergencyCreatedJob extends Job{
    @Autowired
    EmergencyDispatcher emergencyDispatcher;

    @Override
    public void process() throws Exception {
        emergencyDispatcher.dispatchEmergency(
                EmergencyConverter.convertFromEmergencyCreatedEvent(event)
        );
    }
}
