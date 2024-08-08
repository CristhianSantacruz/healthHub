package com.klashz.health;

import io.smallrye.health.checks.UrlHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;


import java.util.Objects;

@ApplicationScoped
public class PatientServiceClientHealthCheck {

    @ConfigProperty(name = "quarkus.rest-client.patient-service-api.url")
    String patientServiceApiUrl;

    @Readiness
    HealthCheck healthCheck() {
        return Objects.requireNonNull(new UrlHealthCheck(patientServiceApiUrl + "/patient/hello")
                .name("Patient Service")
                .requestMethod(HttpMethod.GET)
                .statusCode(200));
    }
}
