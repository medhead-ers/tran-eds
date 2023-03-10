package com.medhead.ers.tran_eds.utils.mock.api_dispatcher;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class AllAPIAvailableDispatcher extends Dispatcher {
    @NotNull
    @Override
    public MockResponse dispatch(@NotNull RecordedRequest request) {
        try {
            if (request.getPath().contains("/grapphopper/matrix")) {
                    return createResponseWithJsonContent(readFile(MOCK_API_RESOURCES_PATH+"graphhopper_get_matrix.json"));
            }
            else if (request.getPath().equals("/hms/hospitals")) {
                return createResponseWithJsonContent(readFile(MOCK_API_RESOURCES_PATH+"hms_get_all_hospitals.json"));
            }
        } catch (Exception ioe) {
            return new MockResponse().setResponseCode(500);
        }

        return new MockResponse().setResponseCode(404);
    }
}
