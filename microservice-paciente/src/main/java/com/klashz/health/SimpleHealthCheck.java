package com.klashz.health;

import com.klashz.IPatientService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class SimpleHealthCheck  implements HealthCheck {

    @Inject
    IPatientService patientService;
    @Override
    public HealthCheckResponse call() {
        return  HealthCheckResponse.named("Patient Service")
                .up()
                .withData("hello","patient service").build();
    }
}
