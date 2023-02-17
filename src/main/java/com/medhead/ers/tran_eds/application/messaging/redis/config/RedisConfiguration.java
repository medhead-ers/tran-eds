package com.medhead.ers.tran_eds.application.messaging.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@Conditional(OnRedisAvailableCondition.class)
public class RedisConfiguration {
    @Autowired
    ApplicationContext context;
    public static final String REDIS_TOPIC = "medhead.ers.messages";

    @Bean
    RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(listenerAdapter, new PatternTopic(REDIS_TOPIC));

        return container;
    }

    @Bean
    RedisConnectionFactory connectionFactory() {
        String redisHost = context.getEnvironment().getProperty("spring.redis.host", "localhost");
        int redisPort = Integer.parseInt(context.getEnvironment().getProperty("spring.redis.port", "6379"));
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageListener messageListener) {
        return new MessageListenerAdapter(messageListener, "receiveMessage");
    }

    @Bean
    MessageListener receiver() {
        return new MessageListener();
    }

    @Bean
    StringRedisTemplate template() {
        return new StringRedisTemplate(connectionFactory());
    }
}
