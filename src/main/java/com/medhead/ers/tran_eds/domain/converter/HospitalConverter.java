package com.medhead.ers.tran_eds.domain.converter;

import com.medhead.ers.tran_eds.domain.dto.Hospital;
import com.medhead.ers.tran_eds.domain.valueObject.Address;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import com.medhead.ers.tran_eds.domain.valueObject.MedicalSpeciality;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.UUID;

public interface HospitalConverter {
    public static Hospital convertFromWebServiceResponse(LinkedHashMap<String, Object> hospital){
        LinkedHashMap<String, Double> gpsCoordinates = (LinkedHashMap<String, Double>) hospital.get("gpsCoordinates");
        LinkedHashMap<String, String> address = (LinkedHashMap<String, String>) hospital.get("address");
        ArrayList<String> medicalSpecialities = (ArrayList<String>) hospital.get("medicalSpecialities");

        Address hospitalAddress = Address.builder()
                .numberAndStreetName(address.get("numberAndStreetName"))
                .addressComplement(address.get("addressComplement"))
                .city(address.get("city"))
                .country(address.get("country"))
                .postCode(address.get("postCode"))
                .build();
        GPSCoordinates hospitalGpsCoordinates = GPSCoordinates.builder()
                .longitude(gpsCoordinates.get("longitude"))
                .latitude(gpsCoordinates.get("latitude"))
                .build();
        HashSet<MedicalSpeciality> hospitalMedicalSpecialities = new HashSet<>();
        for (String medicalSpeciality: medicalSpecialities) {
            hospitalMedicalSpecialities.add(MedicalSpeciality.valueOf(medicalSpeciality));
        }

        return new Hospital(
                UUID.fromString(hospital.get("id").toString()),
                hospital.get("name").toString(),
                hospital.get("code").toString(),
                hospitalAddress,
                hospitalGpsCoordinates,
                hospitalMedicalSpecialities,
                Integer.parseInt(hospital.get("availableEmergencyBedrooms").toString())
        );
    }
}
