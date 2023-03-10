package com.medhead.ers.tran_eds.domain.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.ers.tran_eds.domain.service.definition.GeoMatrixService;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("GraphhopperGeoMatrixService")
public class GraphhopperGeoMatrixService implements GeoMatrixService {
    private final OkHttpClient client = new OkHttpClient();
    @Autowired
    @Qualifier("TrigonometryGeoMatrixService")
    private GeoMatrixService fallBackGeoMatrixService;
    @Value("${graphhoper.api.matrix}")
    private String graphhopperMatrixAPIUrl;
    @Value("${graphhoper.api.secure_key}")
    private String grapphopperAPISecureKey;
    @Value("${graphhoper.api.mode:distances}")
    private String grapphopperAPIMode;

    @Override
    public GPSCoordinates findNearestPoint(GPSCoordinates fromPoint, List<GPSCoordinates> toPoints) {
        Request request = new Request.Builder().url(buildUrl(fromPoint, toPoints)).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Une erreur est survenue lors de l'appel à Grapphopper Matrix API");
            }
            LinkedHashMap<String, Object> grapphopperAPIResponse = (LinkedHashMap<String, Object>) new ObjectMapper().readValue(response.body().string(), Object.class);
            ArrayList<ArrayList<Integer>> distancesArrayResult = (ArrayList<ArrayList<Integer>>) grapphopperAPIResponse.get(grapphopperAPIMode);
            Map<Integer, GPSCoordinates> GPSCoordinatesToDistanceMap = new HashMap<>();
            int index=0;
            for (GPSCoordinates toPoint: toPoints) {
                GPSCoordinatesToDistanceMap.put(distancesArrayResult.get(0).get(index), toPoint);
                index++;
            }
            return new TreeMap<>(GPSCoordinatesToDistanceMap).firstEntry().getValue();
        }
        catch (Exception exception){
            return fallBackGeoMatrixService.findNearestPoint(fromPoint, toPoints);
        }
    }

    private String buildUrl (GPSCoordinates fromPoint, List<GPSCoordinates> toPoints ) {
        String fromPointUrlFrag="&from_point="+fromPoint.getLatitude() + "%2C" +fromPoint.getLongitude();
        String outArrayUrlFrag = "&out_array="+ grapphopperAPIMode;
        String apiKeyUrlFrag = "&key="+ grapphopperAPISecureKey;

        StringBuilder toPointsUrlFrag = new StringBuilder();
        for (GPSCoordinates toPoint: toPoints) {
            toPointsUrlFrag.append("&to_point=").append(toPoint.getLatitude()).append("%2C").append(toPoint.getLongitude());
        }
        return graphhopperMatrixAPIUrl + "?"+ fromPointUrlFrag + toPointsUrlFrag +  outArrayUrlFrag + apiKeyUrlFrag;
    }
}
