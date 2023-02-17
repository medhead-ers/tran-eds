package com.medhead.ers.tran_eds.application.messaging.redis.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnRedisAvailableCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String redisHost = context.getEnvironment().getProperty("spring.redis.host", "localhost");
        String redisPort = context.getEnvironment().getProperty("spring.redis.port", "6379");
        try (StatefulRedisConnection<String, String> redisConnection =
                     RedisClient.create("redis://" + redisHost + ":" + redisPort).connect()){
            return true;
        } catch (RedisConnectionException connectionException) {
            return false;
        }
    }
}