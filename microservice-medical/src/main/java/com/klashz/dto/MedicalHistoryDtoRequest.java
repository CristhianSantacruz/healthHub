package com.klashz.dto;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class MedicalHistoryDtoRequest {
    public String patientId;
    public String diagnosis;
    public String treatment;
    public String notes;
    public String medications;
    public String nextAppointment;
}
