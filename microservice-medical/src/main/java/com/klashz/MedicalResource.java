package com.klashz;

import com.klashz.client.MedicalHistoryService;
import com.klashz.dto.MedicalHistoryDto;
import com.klashz.dto.MedicalHistoryDtoRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.arc.All;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.net.URI;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Path("/medical")
@Tag(name = "Medical Rest Api")
public class MedicalResource {

    @Inject
    IMedicalService medicalService;

    @Inject
    @RestClient
    MedicalHistoryService medicalHistoryService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }
    @GET
    @Path("/prueba/{medicalId}")
    @Fallback(fallbackMethod = "pruebaFallBack")
    @Produces(MediaType.TEXT_PLAIN)
    public List<MedicalHistoryDto> prueba(@PathParam("medicalId") ObjectId medicalId) {
        return medicalHistoryService.getMedicalHistoryByMedicalId(medicalId);
    }

    public List<MedicalHistoryDto> pruebaFallBack(ObjectId medicalId) {
        List<MedicalHistoryDto> medicalHistoryDtos = new ArrayList<>();
        return  medicalHistoryDtos;
    }

    @POST
    @Path("/save")
    @Operation(summary = "Guarda y crea un nuevo medico")
    public Response save(MedicalEntity medicalEntity){
        medicalService.saveMedical(medicalEntity);
        URI uri = URI.create(medicalEntity.id.toString());
        return Response.created(URI.create(medicalEntity.id.toString())).entity(uri).build();
    }
    @GET
    @Path("/{id}")
    @Fallback(fallbackMethod = "getMedicalByIdFallBack")
    @Retry(retryOn = Exception.class,maxRetries = 2)
    @Operation(summary = "Obtiene al medico por el id")
    public Response getMedical(@PathParam("id")ObjectId id) {
        return Response.ok(medicalService.getMedicalByIdWithMedicalHistory(id)).build();
    }

    public Response getMedicalByIdFallBack(ObjectId id) {
        return  Response.ok(medicalService.getMedicalById(id)).build();
    }


    @GET
    @Path("/email/{email}")
    @Operation(summary = "Obtiene al medico por el email que se ha registrado")
    public Response getMedicalByEmail(@PathParam("email")String email) {
        return Response.ok(medicalService.getMedicalByEmail(email)).build();
    }
    @GET
    @Path("/all")
    @Timeout()
    @Retry(maxRetries = 2) //detencion de fallas y ejecuta tanta veces se aposible en este caso 2 veces
    @Fallback(fallbackMethod = "allMedicalFallBack")
    @ResponseStatus(200)
    @Operation(summary = "Obtiene a todos los medicos")
    public List<MedicalEntity> allMedical(){
        return medicalService.getAllMedicals();
    }

    public List<MedicalEntity> allMedicalFallBack(){
      return List.of();
    }

    @GET
    @Path("/all/specialization/")
    @Operation(summary = "Obtiene a los medico por specializacion")
    public Response allMedicalBySpecialization(@QueryParam("special") String specialization){
        return Response.ok(medicalService.searchMedicalBySpecialization(specialization)).build();
    }

    @GET
    @Path("/all/department")
    @Operation(summary = "Obtiene a los medico por departamento")
    public Response allMedicalByDepartment(@QueryParam("department") String department){
        return Response.ok(medicalService.searchMedicalByDepartment(department)).build();
    }

    @GET
    @Path("/all/qualification")
    @Operation(summary = "Verifica a los medicos por cualificacion")
    public Response allMedicalByQualification(@QueryParam("qualification") String qualification){
        return Response.ok(medicalService.getMedicalByQualification(qualification)).build();
    }

    @PUT
    @Path("/update/{id}")
    @Operation(summary = "Actualizar los datos del usuario")
    public Response updateMedical(@PathParam("id")ObjectId id, MedicalEntity medicalEntity){
        medicalService.updateMedical(id, medicalEntity);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/add/{id}/qualification")
    @Operation(summary = "Agregar una nueva cualificacion a un medico")
    @Metered(description = "Eficiencia del endpoint que agrega cualificaciones aun medico")
    public Response addQualification(@PathParam("id") ObjectId id ,@QueryParam("qualification") String qualification){
        boolean result = medicalService.addQualificationToMedical(id, qualification);

        return result ? Response.status(Response.Status.OK).entity(result).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Operation(summary = "Elimina el medico por el id")
    public Response deleteMedical(@PathParam("id")ObjectId id) {
        boolean result = medicalService.deleteMedical(id);
        return result ? Response.status(Response.Status.OK).entity(result).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/create/history/{medicalId}")
    @Operation(summary = "Crea un registro medico", description = "El medico crea un registro medico para el paciente y con sus respectivas notas y poniendo la siguiente cita")
    public Response createHistory(@PathParam("medicalId") ObjectId medicalId, MedicalHistoryDtoRequest medicalHistoryDto){

        URI uri =  medicalService.createMedicalHistory(medicalId, medicalHistoryDto);
        return Response.created(uri).build();
    }

























}
