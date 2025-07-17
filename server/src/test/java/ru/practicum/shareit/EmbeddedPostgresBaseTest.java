package ru.practicum.shareit;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

public abstract class EmbeddedPostgresBaseTest {

    protected static EmbeddedPostgres embeddedPostgres;

    @BeforeAll
    static void startPostgres() throws IOException {
        embeddedPostgres = EmbeddedPostgres.builder()
                .setPort(0) // случайный свободный порт
                .start();
    }

    @AfterAll
    static void stopPostgres() throws IOException {
        if (embeddedPostgres != null) {
            embeddedPostgres.close();
        }
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> embeddedPostgres.getJdbcUrl("postgres", "postgres"));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "");
    }
}
