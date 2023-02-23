package com.medhead.ers.tran_eds.application.messaging.service.implementation;

import com.medhead.ers.tran_eds.application.messaging.event.Event;
import com.medhead.ers.tran_eds.application.messaging.job.Job;
import com.medhead.ers.tran_eds.application.messaging.service.definition.JobMapper;
import org.springframework.stereotype.Service;

@Service
public class JobMapperImpl implements JobMapper {
    @Override
    public Job createJobFromEvent(Event event) throws Exception {
        Class<?> jobClass = Class.forName(Job.class.getPackageName() + "." + getJobNameFromEvent(event));
        return (Job) jobClass.getDeclaredConstructor(Event.class).newInstance(event);
    }

    private String getJobNameFromEvent(Event event){
        return  event.getEventType() + "Job";
    }

    public boolean checkIfJobExistForEvent(Event event){
        try {
            Class.forName(Job.class.getPackageName() + "." + getJobNameFromEvent(event));
            return true;
        }
        catch (ClassNotFoundException e){
            return false;
        }
    }
}
