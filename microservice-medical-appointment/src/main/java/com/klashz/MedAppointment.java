package com.klashz;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@MongoEntity(collection = "medicalAppointment")
public class MedAppointment extends PanacheMongoEntity {

    @NotNull(message = "Es necesario conocer el dia de la cita")
    public LocalDate date;
    @NotNull(message = "Es necesario la hora de la cita")
    public LocalTime hour;
    @NotNull
    public MedicalAppointmentStatus status;
    @NotNull @NotBlank(message = "Cual es la razon de la cita")
    public String reason;
    @NotNull(message = "Necesitamos conocer el medico a cargo")
    public ObjectId idMedical;
    @NotNull(message = "El paciente que se le hara la consulta")
    public String idPatient;
    @NotNull(message = "Es necesario conocer la sala de la cita ") @NotBlank
    public String consultationRoom;


}
