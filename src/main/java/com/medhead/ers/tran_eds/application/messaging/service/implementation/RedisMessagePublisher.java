package com.medhead.ers.tran_eds.application.messaging.service.implementation;

import com.medhead.ers.tran_eds.application.messaging.exception.MessagePublicationFailException;
import com.medhead.ers.tran_eds.application.messaging.message.Message;
import com.medhead.ers.tran_eds.application.messaging.redis.config.RedisConfiguration;
import com.medhead.ers.tran_eds.application.messaging.service.definition.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public void publish(Message message) throws MessagePublicationFailException {
        try {
            ctx.getBean(StringRedisTemplate.class).convertAndSend(RedisConfiguration.REDIS_TOPIC, message.toString());
        }
        catch (Exception exception){
            throw new MessagePublicationFailException(exception);
        }
    }
}
