package com.klashz.dto;

import org.bson.types.ObjectId;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

public class MedicalHistoryDto {
    public ObjectId id;
    public String patientId;
    public LocalDateTime dateCreation;
    public String diagnosis;
    public String treatment;
    public String notes;
    public List medications;
    public String nextAppointment;
}
