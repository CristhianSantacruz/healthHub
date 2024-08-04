package com.klashz;

import com.klashz.client.MyRemoteService;
import com.klashz.dto.MedicalHistoryEntityDtoRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.net.URI;
import java.util.List;

@Path("/history")
public class MedicalHistoryResource {

    @Inject
    @RestClient
    MyRemoteService myRemoteService;

    @Inject
    IMedicalHistoryService iMedicalHistoryService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/prueba")
    public Response getExtensions(@QueryParam("id") String id) {
        return Response.ok(myRemoteService.getExtensionsById(id)).build();

    }

    @GET
    @Path("/{id}")
    public Response getMedicalHistory(@PathParam("id") ObjectId id) {
        return Response.ok(iMedicalHistoryService.getMedicalHistory(id)).build();
    }

    @GET
    @Path("/all")
    @ResponseStatus(200)
    public List<MedicalHistoryEntity> getAllMedicalHistory(){
        return iMedicalHistoryService.getAllMedicalHistory();
    }
    @GET
    @Path("/patient/{patientId}")
    public Response getPatientMedicalHistory(@PathParam("patientId") String patientId) {
        return Response.ok(iMedicalHistoryService.getMedicalHistoryByPatientId(patientId)).build();
    }
    @GET
    @Path("/medical/{medicalId}")
    public Response getMedicalHistoryById(@PathParam("medicalId") ObjectId medicalId) {
        return Response.ok(iMedicalHistoryService.getMedicalHistoryByMedicalId(medicalId)).build();
    }

    @GET
    @Path("/medical/patient/{idPatient}")
    public Response getPatientMedicalHistoryById(@PathParam("idPatient") String idPatient,@QueryParam("dia") String diagnosis) {
        return Response.ok(iMedicalHistoryService.getMedicalHistoryByDiagnosisByPatientId(idPatient,diagnosis)).build();
    }

    @POST
    @Path("/create/{medicalId}")
    public Response createMedicalHistory(@PathParam("medicalId") ObjectId medicalId, MedicalHistoryEntityDtoRequest medicalHistoryEntityDtoRequest) {
        MedicalHistoryEntity medicalHistoryEntity  = iMedicalHistoryService.createMedicalHistory(medicalId,medicalHistoryEntityDtoRequest);
        return Response.created(URI.create(String.valueOf(medicalHistoryEntity.getMedicalId()))).build();
    }

    @PUT
    @Path("/update/{id}")
    @ResponseStatus(205)
    public void updateMedicalHistoryById(@PathParam("id") ObjectId idMedicalHistory,MedicalHistoryEntity newDataMedicalHistoryEntity) {

        iMedicalHistoryService.updateMedicalHistory(idMedicalHistory,newDataMedicalHistoryEntity);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteMedicalHistoryById(@PathParam("id") ObjectId id) {
        return iMedicalHistoryService.deleteMedicalHistory(id) ? Response.ok(true).build() : Response.status(Response.Status.NOT_FOUND).entity(false).build();
    }

}
