package com.medhead.ers.tran_eds_test.utils.mock.api_dispatcher;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

public class AllAPIUnavailableDispatcher extends Dispatcher {
    @NotNull
    @Override
    public MockResponse dispatch(@NotNull RecordedRequest request) {
        try {
            if (request.getPath().contains("/grapphopper/matrix")) {
                    return createServiceUnavailableResponse();
            }
            else if (request.getPath().equals("/hms/hospitals")) {
                return createServiceUnavailableResponse();
            }
        } catch (Exception ioe) {
            return new MockResponse().setResponseCode(500);
        }

        return new MockResponse().setResponseCode(404);
    }
}
