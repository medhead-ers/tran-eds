package com.medhead.ers.tran_eds.utils.mock.api_dispatcher;

import okhttp3.mockwebserver.MockResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

abstract class Dispatcher extends okhttp3.mockwebserver.Dispatcher {
    protected static final String MOCK_API_RESOURCES_PATH = "src/test/resources/mock/api/";

    protected String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    protected MockResponse createResponseWithJsonContent(String responseBody) {
        return new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(200)
                .setBody(responseBody);
    }

    protected MockResponse createServiceUnavailableResponse() {
        return new MockResponse()
                .setResponseCode(500);
    }
}
