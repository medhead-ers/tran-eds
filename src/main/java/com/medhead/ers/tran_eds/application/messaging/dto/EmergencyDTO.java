package com.medhead.ers.tran_eds.application.messaging.dto;
import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.domain.valueObject.EmergencyStatus;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import com.medhead.ers.tran_eds.domain.valueObject.MedicalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.UUID;
@Getter
@AllArgsConstructor
public class EmergencyDTO {
    private final UUID id;
    private final String description;
    private final String patientId;
    private final GPSCoordinates gpsCoordinates;
    private final MedicalSpeciality medicalSpeciality;
    private final EmergencyStatus status;

    public static EmergencyDTO mapFromEmergencyCreatedEvent(Event event){
        LinkedHashMap<String, Object> emergency = (LinkedHashMap<String, Object>) event.getMetadata().get("emergency");
        LinkedHashMap<String, Double> gpsCoordinates = (LinkedHashMap<String, Double>) emergency.get("gpsCoordinates");

        return new EmergencyDTO(
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
