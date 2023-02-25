package com.medhead.ers.tran_eds.domain.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.ers.tran_eds.domain.converter.HospitalConverter;
import com.medhead.ers.tran_eds.domain.dto.Hospital;
import com.medhead.ers.tran_eds.domain.service.definition.HospitalService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {
    private final OkHttpClient client = new OkHttpClient();
    @Value("${medhead.api.hms}")
    private String HMSAPIUrl;

    @Override
    public List<Hospital> getAllHospitals() throws Exception {
        Request request = new Request.Builder().url(HMSAPIUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException(" Un problème est survenu lors de la récupération de la liste des hopitaux : " + response);
            ArrayList<LinkedHashMap<String, Object>> webHospitalsList = (ArrayList<LinkedHashMap<String, Object>>) new ObjectMapper().readValue(response.body().string(), Object.class);
            return webHospitalsList.stream().map(HospitalConverter::convertFromWebServiceResponse).toList();
        }
    }
}
