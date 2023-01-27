package com.baakapp.urlshortenerservice.container;

import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;

public abstract class MongoContainer {

    static final GenericContainer mongo;

    static {
        mongo = new GenericContainer("mongo:6.0.3")
                .withExposedPorts(27017)
                .withEnv("MONGO_INITDB_ROOT_USERNAME", "root")
                .withEnv("MONGO_INITDB_ROOT_PASSWORD", "rootpassword");
    }

    static {
        Startables.deepStart(mongo).join();
    }

    @DynamicPropertySource
    static void mongoProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongo::getHost);
        registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
        registry.add("spring.data.mongodb.database", () -> "app_db");
        registry.add("spring.data.mongodb.username", () -> "root");
        registry.add("spring.data.mongodb.password", () -> "rootpassword");
        registry.add("spring.data.mongodb.authentication-database", () -> "admin");
        registry.add("spring.data.mongodb.auto-index-creation", () -> "true");
    }
}