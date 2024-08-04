package com.klashz.dto;

import org.bson.types.ObjectId;

public record MedicalHistoryEntityDtoRequest(String patientId,String diagnosis,String treatment,String medications,String notes,String nextAppointment) {
}
