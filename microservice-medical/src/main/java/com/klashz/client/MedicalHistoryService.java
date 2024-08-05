package com.klashz.client;

import com.klashz.dto.MedicalHistoryDto;
import com.klashz.dto.MedicalHistoryDtoRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Path("/history")
@RegisterRestClient(configKey = "medical-history-service-api")
public interface MedicalHistoryService {

    @GET
    @Path("/medical/{medicalId}")
    List<MedicalHistoryDto> getMedicalHistoryByMedicalId(@PathParam("medicalId") ObjectId medicalId);

    @POST
    @Path("/create/{medicalId}")
    URI createMedicalHistory(@PathParam("medicalId")ObjectId medicalId, MedicalHistoryDtoRequest medicalHistoryDto);
}
