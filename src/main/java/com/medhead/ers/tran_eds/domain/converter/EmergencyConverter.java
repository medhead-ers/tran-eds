package com.medhead.ers.tran_eds.domain.converter;

import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.valueObject.EmergencyStatus;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import com.medhead.ers.tran_eds.domain.valueObject.MedicalSpeciality;

import java.util.LinkedHashMap;
import java.util.UUID;

public class EmergencyConverter {
    public static Emergency convertFromEmergencyCreatedEvent(Event event){
        LinkedHashMap<String, Object> emergency = (LinkedHashMap<String, Object>) event.getMetadata().get("emergency");
        LinkedHashMap<String, Double> gpsCoordinates = (LinkedHashMap<String, Double>) emergency.get("gpsCoordinates");

        return new Emergency(
                UUID.fromString(emergency.get("id").toString()),
                emergency.get("description").toString(),
                emergency.get("patientId").toString(),
                GPSCoordinates.builder()
                        .longitude(gpsCoordinates.get("longitude"))
                        .latitude(gpsCoordinates.get("latitude"))
                        .build(),
                MedicalSpeciality.valueOf(emergency.get("medicalSpeciality").toString()),
                EmergencyStatus.valueOf(emergency.get("status").toString())
        );
    }
}
