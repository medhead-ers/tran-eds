package com.medhead.ers.tran_eds.domain.service.definition;

import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;

import java.io.IOException;
import java.util.List;

public interface GeoMatrixService {
    GPSCoordinates findNearestPoint(GPSCoordinates fromPoint, List<GPSCoordinates> toPoints);
}
