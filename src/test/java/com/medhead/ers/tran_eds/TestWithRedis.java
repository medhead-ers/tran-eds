package com.medhead.ers.tran_eds;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
public abstract class TestWithRedis {
    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis"))
                        .withExposedPorts(6379).withPrivilegedMode(true);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }
}
