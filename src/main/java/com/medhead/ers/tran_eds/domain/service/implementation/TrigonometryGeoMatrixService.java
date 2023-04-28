package com.medhead.ers.tran_eds.domain.service.implementation;

import com.medhead.ers.tran_eds.domain.service.definition.GeoMatrixService;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service("TrigonometryGeoMatrixService")
public class TrigonometryGeoMatrixService implements GeoMatrixService {
    @Override
    public GPSCoordinates findNearestPoint(GPSCoordinates fromPoint, List<GPSCoordinates> toPoints) {
        Map<Double, GPSCoordinates> GPSCoordinatesToTrigonometricDistanceMap = new HashMap<>();
        for (GPSCoordinates toPoint: toPoints) {
            double latitude = fromPoint.getLatitude() - toPoint.getLatitude();
            double longitude = fromPoint.getLongitude() - toPoint.getLongitude();
            double distance = Math.sqrt(Math.pow(latitude, 2) + Math.pow(longitude, 2));
            GPSCoordinatesToTrigonometricDistanceMap.put(distance, toPoint);
        }
        return new TreeMap<>(GPSCoordinatesToTrigonometricDistanceMap).firstEntry().getValue();
    }
}
