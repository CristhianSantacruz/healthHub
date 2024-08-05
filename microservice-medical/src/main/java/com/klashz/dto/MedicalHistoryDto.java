package com.klashz.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MedicalHistoryDto {
    private ObjectId id;
    private String patientId;
    private PatientDto patientDto;
    private LocalDateTime dateCreation;
    private String diagnosis;
    private String treatment;
    private String notes;
    private List<String> medications;
    private String nextAppointment;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public PatientDto getPatientDto() {
        return patientDto;
    }

    public void setPatientDto(PatientDto patientDto) {
        this.patientDto = patientDto;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List getMedications() {
        return medications;
    }

    public void setMedications(List medications) {
        this.medications = medications;
    }

    public String getNextAppointment() {
        return nextAppointment;
    }

    public void setNextAppointment(String nextAppointment) {
        this.nextAppointment = nextAppointment;
    }
}
