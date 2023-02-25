package com.medhead.ers.tran_eds.domain.service.definition;

import com.medhead.ers.tran_eds.domain.dto.Emergency;
import com.medhead.ers.tran_eds.domain.dto.Hospital;

public interface EmergencyDispatcher {
    Hospital dispatchEmergency(Emergency emergency) throws Exception;
}
