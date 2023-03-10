package com.medhead.ers.tran_eds.application.messaging.job;

import com.medhead.ers.tran_eds.application.messaging.event.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Job {
    protected Event event;

    public abstract void process() throws Exception;
}
