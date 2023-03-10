package com.medhead.ers.tran_eds.units;

import com.medhead.ers.tran_eds.domain.service.implementation.TrigonometryGeoMatrixService;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TrigonometryGeoMatrixServiceTest {
    private final TrigonometryGeoMatrixService trigonometryGeoMatrixService = new TrigonometryGeoMatrixService();

    @Test
    void test_findNearestPoint(){
        ArrayList<GPSCoordinates> toPoints = new ArrayList<>();

        // Given
        GPSCoordinates fromPoint = GPSCoordinates.builder().latitude(0).longitude(0).build();
        GPSCoordinates nearestPoint = GPSCoordinates.builder().latitude(10).longitude(10).build();
        toPoints.add(nearestPoint);
        toPoints.add(GPSCoordinates.builder().latitude(-20).longitude(10).build());
        toPoints.add(GPSCoordinates.builder().latitude(15).longitude(12).build());
        toPoints.add(GPSCoordinates.builder().latitude(10).longitude(-30).build());

        // When
        GPSCoordinates findPoint = trigonometryGeoMatrixService.findNearestPoint(fromPoint, toPoints);
        // Then
        Assertions.assertEquals(findPoint, nearestPoint);
    }


}
