package com.klashz;

import org.eclipse.microprofile.metrics.annotation.Counted;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.Optional;

@Path("/patient")
@Tag(name = "Patient Api",description = "Rest Api for Patients")
public class PatientResource {

    @Inject
    IPatientService patientService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/saludar")
    public Response saludar(@QueryParam("name") String nombre){
        String mensaje = Optional.ofNullable(nombre)
                .filter(n -> !n.trim().isEmpty())
                .map(n -> "Hola como estas " + n)
                .orElse("Hola usuario anonimo");
        return Response.ok(mensaje).build();
    }
    @Operation(summary = "Busca a un  paciente por su dni",description = "Recibe el dni o identificador de un paciente y los devuelve si existe.")
    @GET
    @Path("/{dni}")
    @Counted(name = "countDniSearch",description = "Cuantas veces se busca por dni a un paciente")
    @Timed(name = "timeDniSearch",description = "tiempo de resultado de buscar un paciente")
    public Response getPatient(@PathParam("dni") String dni){
        Optional<PatientEntity> patientEntityOptional = patientService.getPatientByDni(dni);
        if(patientEntityOptional.isPresent()){
            return Response.ok(patientEntityOptional.get()).build();
        }
        return Response.status(404).build();
    }
    @GET
    @Path("/email/{email}")
    @Counted(name = "countEmailSearch",description = "Cuantas veces se busca a los paciente por email")
    @Timed(name = "timeEmailSearch",description = "tiempo de resultao de buscar un paciente por el email")
    public Response getPatientByEmail(@PathParam("email") String email){
        Optional<PatientEntity> patientEntityOptional = patientService.getPatientByEmail(email);
        if(patientEntityOptional.isPresent()){
            return Response.ok(patientEntityOptional.get()).build();
        }
        return Response.status(404).build();
    }

    @GET()
    @Path("/all")
    public Response getAllPatient(){return Response.ok(patientService.getAllPatients()).build();}

    @GET()
    @Path("/all/active")
    public Response getAllPatientActive(){return Response.ok(patientService.getPatientsActive()).build();}


    @GET()
    @Path("/all/bloodType/{blood}")
    public Response getAllPatientByBloodType(@PathParam("blood") String blood){return Response.ok(patientService.getPatientForHisBloodType(blood)).build();}

    @POST()
    @Path("/save")
    public Response savePatient(@Valid PatientEntity patientEntity){
        patientService.savePatient(patientEntity);
        return Response.status(201).entity(patientEntity).build();
    }

    @PUT()
    @Path("/update/{dni}")
    public Response updatePatient(@PathParam("dni") String dni, @Valid PatientEntity patientEntity){
        patientService.updatePatient(dni,patientEntity);
        return Response.status(201).entity(patientEntity).build();
    }
    @PATCH()
    @Path("/update/{dni}/{phone}")
    @Counted(name = "countUpdatePhone",description = "cuantas personas cambian su  numero de telefono")
    @Metered(name = "medido",displayName = "Eficiencia de la respuesta",description = "Muestra que tan eficiente es un endpoint")
    public Response updatePhoneNumber(@PathParam("phone") String newPhone,@PathParam("dni") String dni){
        patientService.updatePhoneNumberByPatient(dni,newPhone);
        return Response.ok(true).build();
    }


    @DELETE()
    @Path("/delete/{id}")
    public Response deletePatient(@PathParam("id") String id){
        return PatientEntity.deleteById(id) ? Response.ok().build() : Response.status(404).build();
    }

}
