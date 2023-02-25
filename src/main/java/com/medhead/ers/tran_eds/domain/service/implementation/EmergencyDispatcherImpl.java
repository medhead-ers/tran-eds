package com.medhead.ers.tran_eds.domain.service.implementation;

import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.dto.Hospital;
import com.medhead.ers.tran_eds.domain.service.definition.EmergencyDispatcher;
import com.medhead.ers.tran_eds.domain.service.definition.GeoMatrixService;
import com.medhead.ers.tran_eds.domain.service.definition.HospitalService;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyDispatcherImpl implements EmergencyDispatcher {
    @Autowired
    HospitalService hospitalService;
    @Autowired
    GeoMatrixService geoMatrixService;
    List<Hospital> hospitalsList;

    @Override
    public Hospital dispatchEmergency(Emergency emergency) throws Exception {
        List<Hospital> availableHospitalsList = filterHospitalByCapabilitiesForEmergency(emergency, getFreshHospitalsList());
        Hospital nearestAvailableHospital = findNearestHospitalForEmergency(emergency, availableHospitalsList);
        return null;
    }

    private Hospital findNearestHospitalForEmergency(Emergency emergency, List<Hospital> availableHospitalsList) throws Exception {
        List<GPSCoordinates> hospitalsGPSCoordinatesList = availableHospitalsList.stream().map(Hospital::getGpsCoordinates).toList();
        GPSCoordinates nearestPoint = geoMatrixService.findNearestPoint(emergency.getGpsCoordinates(), hospitalsGPSCoordinatesList);
        return availableHospitalsList.stream().filter(hospital -> hospital.getGpsCoordinates() == nearestPoint)
                .findFirst().orElseThrow();
    }

    private List<Hospital> getFreshHospitalsList() throws Exception {
        return hospitalService.getAllHospitals();
    }

    private List<Hospital> filterHospitalByCapabilitiesForEmergency(Emergency emergency, List<Hospital> hospitalsList) {
        return hospitalsList.stream().filter(hospital ->
                        hospital.getMedicalSpecialities().contains(emergency.getMedicalSpeciality())
                        && hospital.getAvailableEmergencyBedrooms() > 0
        ).toList();
    }
}
