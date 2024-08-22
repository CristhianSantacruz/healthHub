package com.klashz;

import com.klashz.dto.MedAppointmentDtoRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Path("/appointment")
public class ExampleResource {

    @Inject
    IMedAppointmentService iMedAppointmentService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }


    @POST
    @Path("/create")
    public Response createMedicalAppoinment(MedAppointmentDtoRequest medAppointmentDtoRequest) {

        return Response.status(201).entity(iMedAppointmentService.createMedicalAppointment(medAppointmentDtoRequest)).build();
    }
    @GET
    @Path("/{id}")
    public Response getMedicalAppointmentById(@PathParam("id") ObjectId id) {
        Optional<MedAppointment> optionalMedAppointment = iMedAppointmentService.getMedAppointmentById(id);
        if (optionalMedAppointment.isPresent()) {
            return Response.status(200).entity(optionalMedAppointment.get()).build();
        }
        return Response.status(404).build();
    }
    @GET
    @ResponseStatus(HttpResponseStatus.Ok)
    public List<MedAppointment> getALlMedAppointments(){
        return iMedAppointmentService.getAllMedAppointments();
    }

    @GET
    @Path("/patient/{id}")
    public Response getPatientMedicalAppointmentBys(@PathParam("id") String dni) {
        return Response.ok(iMedAppointmentService.getMedAppointmentsByPatientId(dni)).build();
    }

    @GET
    @Path("/medical/{id}")
    public Response getMedicalByAppointmentBys(@PathParam("id") ObjectId id) {
        return Response.ok(iMedAppointmentService.getMedAppointmentsByDoctorId(id)).build();
    }

    @GET
    @Path("/status")
    public Response getMedicalAppointmentStatus(@QueryParam("status") String status) {
        return Response.ok().entity(iMedAppointmentService.getMedAppointmentsByStatus(status)).build();
    }

    @PUT
    @Path("/update/{id}")
    public Response updateMedicalAppointment(@PathParam("id") ObjectId id , MedAppointment newAppointment){

        boolean result = iMedAppointmentService.updateMedicalAppointment(id,newAppointment);
        return result ? Response.status(200).entity("Actualizado").build() : Response.status(404).build();

    }

    @PATCH
    @Path("/completed/{id}")
    public Response completeMedicalAppointment(@PathParam("id") ObjectId id){
        boolean result = iMedAppointmentService.completedMedicalAppointment(id);
        return result ? Response.status(200).entity("Actualizado").build() : Response.status(404).build();
    }

    @PATCH
    @Path("/canceled/{id}")
    public Response canceledMedicalAppointment(@PathParam("id") ObjectId id){
        boolean result = iMedAppointmentService.cancelMedicalAppointment(id);
        return result ? Response.status(200).entity("Actualizado").build() : Response.status(404).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteMedicalAppointment(@PathParam("id") ObjectId id){
        boolean result = iMedAppointmentService.deleteMedicalAppointment(id);
        return result ? Response.ok().build() : Response.status(404).build();
    }


}
