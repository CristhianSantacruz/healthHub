package com.klashz.client;

import com.klashz.dto.PatientReservedAppointment;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9070")
@Path("/appointment")
public interface MedicalAppointmentClient {

    @PATCH
    @Path("/reserved/{id}")
    boolean reservedMedicalAppointment(@PathParam("id") ObjectId id , PatientReservedAppointment patientReservedAppointment);

}
