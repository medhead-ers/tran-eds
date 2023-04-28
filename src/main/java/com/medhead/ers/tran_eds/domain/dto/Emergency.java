package com.medhead.ers.tran_eds.domain.dto;

import com.medhead.ers.tran_eds.domain.valueObject.EmergencyStatus;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import com.medhead.ers.tran_eds.domain.valueObject.MedicalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class Emergency {
    private final UUID id;
    private final String description;
    private final String patientId;
    private final GPSCoordinates gpsCoordinates;
    private final MedicalSpeciality medicalSpeciality;
    private final EmergencyStatus status;
}
