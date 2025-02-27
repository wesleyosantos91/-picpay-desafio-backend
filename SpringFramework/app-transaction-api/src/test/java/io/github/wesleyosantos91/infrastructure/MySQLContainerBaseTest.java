package io.github.wesleyosantos91.infrastructure;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MySQLContainerBaseTest {

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.29")
            .withDatabaseName("transactions")
            .withUsername("app")
            .withPassword("app");

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:mysql://" + mySQLContainer.getHost() + ":" + mySQLContainer.getMappedPort(3306) + "/transactions?allowPublicKeyRetrieval=true&useSSL=false");
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.flyway.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.flyway.user", mySQLContainer::getUsername);
        registry.add("spring.flyway.password", mySQLContainer::getPassword);
    }
}