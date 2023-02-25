package com.medhead.ers.tran_eds.domain.service.implementation;

import com.medhead.ers.tran_eds.domain.service.definition.GeoMatrixService;
import com.medhead.ers.tran_eds.domain.valueObject.GPSCoordinates;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GraphhopperGeoMatrixService implements GeoMatrixService {
    private final OkHttpClient client = new OkHttpClient();
    @Value("${graphhoper.api.matrix}")
    private String GraphhopperMatrixAPIUrl;
    @Value("${graphhoper.api.secure_key}")
    private String GrapphopperAPISecureKey;
    @Value("${graphhoper.api.mode:distances}")
    private String GrapphopperAPIMode;

    @Override
    public GPSCoordinates findNearestPoint(GPSCoordinates fromPoint, List<GPSCoordinates> toPoints) throws Exception {
        Request request = new Request.Builder().url(buildUrl(fromPoint, toPoints)).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException(" Un problème est survenu lors de la récupération de la liste des hopitaux : " + response);
        }
        return null;
    }

    private String buildUrl (GPSCoordinates fromPoint, List<GPSCoordinates> toPoints ) {
        String fromPointUrlFrag="&from_point="+fromPoint.getLatitude() + "%2C" +fromPoint.getLongitude();
        String outArrayUrlFrag = "&out_array="+GrapphopperAPIMode;
        String apiKeyUrlFrag = "&key="+GrapphopperAPISecureKey;

        StringBuilder toPointsUrlFrag = new StringBuilder();
        for (GPSCoordinates toPoint: toPoints) {
            toPointsUrlFrag.append("&to_point=").append(toPoint.getLatitude()).append("%2C").append(toPoint.getLongitude());
        }
        return GraphhopperMatrixAPIUrl + "?"+ fromPointUrlFrag + toPointsUrlFrag +  outArrayUrlFrag + apiKeyUrlFrag;
    }
}
