package com.cooperhost.logistics.shared.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class IntegrationTestConfig {

    private static final PostgreSQLContainer postgresSQLContainer;

    static {
        postgresSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.2-alpine"));
        postgresSQLContainer.start();
    }

    @DynamicPropertySource
    @SuppressWarnings("unused")
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgresSQLContainer::getPassword);
    }
}
