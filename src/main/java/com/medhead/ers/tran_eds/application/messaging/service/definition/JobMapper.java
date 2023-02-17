package com.medhead.ers.tran_eds.application.messaging.service.definition;

import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.application.messaging.job.Job;

public interface JobMapper {
    Job createJobFromEvent(Event event) throws Exception;
}
