package com.medhead.ers.tran_eds.domain.dto;

import com.medhead.ers.tran_eds.domain.valueObject.Address;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import com.medhead.ers.tran_eds.domain.valueObject.MedicalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Hospital {
    private UUID id;
    private String name;
    private String code;
    private Address address;
    private GPSCoordinates gpsCoordinates;
    private Set<MedicalSpeciality> medicalSpecialities;
    private int availableEmergencyBedrooms;
}
