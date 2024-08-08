package com.klashz.health;

import io.smallrye.health.checks.UrlHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import java.util.Objects;

@ApplicationScoped
public class MedicalHistoryClientHealthCheck {

    @ConfigProperty(name = "quarkus.rest-client.medical-history-service-api.url")
    String medicalHistoryServiceUrl;

    @Readiness
    HealthCheck healthCheck() {
        return Objects.requireNonNull(new UrlHealthCheck(medicalHistoryServiceUrl + "/history/hello")
                .name("Medical History Service")
                .requestMethod(HttpMethod.GET)
                .statusCode(200));

    }
}
