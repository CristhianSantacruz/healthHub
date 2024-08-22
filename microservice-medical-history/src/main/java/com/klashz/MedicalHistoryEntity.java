package com.klashz;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@MongoEntity(collection = "medicalHistory")
public  class MedicalHistoryEntity {

    @NotNull @NotBlank
    private ObjectId id;
    @NotNull(message = "EL identificador del medico no puede ser nulo")
    @NotBlank(message = "El identificador del medico es necesario")
    private ObjectId medicalId;
    @NotNull(message = "El identificador del paciente no puede ser nulo")
    @NotBlank(message = "El identificador del paciente es necesario")
    private String patientId;
    private LocalDateTime dateCreation;
    @NotNull @NotBlank
    private String diagnosis;
    private String treatment;
    private List<String> medications;
    private String notes;
    private LocalDate nextAppointment;

    public MedicalHistoryEntity(){

    }

    public MedicalHistoryEntity(ObjectId medicalId, String patientId, LocalDateTime dateCreation, String diagnosis, String treatment, List<String> medications, String notes, LocalDate nextAppointment) {
        this.medicalId = medicalId;
        this.patientId = patientId;
        this.dateCreation = dateCreation;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medications = medications;
        this.notes = notes;
        this.nextAppointment = nextAppointment;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(ObjectId medicalId) {
        this.medicalId = medicalId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getNextAppointment() {
        return nextAppointment;
    }

    public void setNextAppointment(LocalDate nextAppointment) {
        this.nextAppointment = nextAppointment;
    }
}
