package com.departmentstore.api.infrastructure.monitoring;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthIndicator(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {

        try {

            Integer result =
                    jdbcTemplate.queryForObject(
                            "SELECT 1",
                            Integer.class
                    );

            if (result != null && result == 1) {

                return Health.up()
                        .withDetail(
                                "database",
                                "PostgreSQL reachable"
                        )
                        .build();
            }

            return Health.down()
                    .withDetail(
                            "database",
                            "Unexpected result"
                    )
                    .build();

        } catch (Exception ex) {

            return Health.down(ex)
                    .withDetail(
                            "database",
                            "Connection failed"
                    )
                    .build();
        }
    }
}