package com.klashz.client;

import com.klashz.dto.PatientDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Optional;

@Path("/patient")
@RegisterRestClient(configKey = "patient-service-api")
public interface PatientService {

    @GET
    @Path("/{id}")
    Optional<PatientDto> getPatientByDni(@PathParam("id") String dni);
}
