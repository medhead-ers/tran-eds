package com.medhead.ers.tran_eds.domain.service.definition;

import com.medhead.ers.tran_eds.domain.dto.Hospital;

import java.io.IOException;
import java.util.List;

public interface HospitalService {
    List<Hospital> getAllHospitals() throws IOException;
}
