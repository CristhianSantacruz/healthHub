package com.klashz.dto;

import org.bson.types.ObjectId;

public record MedAppointmentDtoRequest(String date, String hour, String reason, ObjectId idMedical,String idPatient,String consultationRoom) {
}
