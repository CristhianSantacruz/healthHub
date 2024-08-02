package com.klashz;

import com.klashz.exceptions.PatientNotExistsException;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PatientService implements IPatientService {


    @Override
    public Optional<PatientEntity> getPatientByDni(String dni) throws PatientNotExistsException {
        Optional<PatientEntity> patientOptional = PatientEntity.findByIdOptional(dni);
        if(patientOptional.isEmpty()) throw new PatientNotExistsException();
        return patientOptional;
    }

    @Override
    public Optional<PatientEntity> getPatientByEmail(String email) throws PatientNotExistsException {
        Optional<PatientEntity> patientOptional = PatientEntity.find("email",email).firstResultOptional();
        if(patientOptional.isEmpty()) throw new PatientNotExistsException();
        return patientOptional;
    }

    @Override
    public List<PatientEntity> getPatientsActive() {
        return PatientEntity.list("statusPatient",StatusPatient.ACTIVE);
    }

    @Override
    public List<PatientEntity> getPatientForHisBloodType(String bloodType) {
        return PatientEntity.list("bloodType",bloodType);
    }

    @Override
    public List<PatientEntity> getAllPatients() {
        return PatientEntity.listAll();
    }

    @Override
    public void updatePatient(String dni, PatientEntity patient) throws PatientNotExistsException {
        Optional<PatientEntity> patientEntityOptional = PatientEntity.findByIdOptional(dni);
        if(patientEntityOptional.isEmpty()) throw new PatientNotExistsException();
        PatientEntity patientEntity = patientEntityOptional.get();
        patientEntity.name = patient.name;
        patientEntity.phone = patient.phone;
        patientEntity.gender = patient.gender;
        patientEntity.age = patient.age;
        patientEntity.bloodType = patient.bloodType;
        patientEntity.password = patient.password;
        patientEntity.address = patient.address;
        patientEntity.emergencyContact = patient.emergencyContact;
        patientEntity.statusPatient = patient.statusPatient;

        PatientEntity.update(patientEntity);

    }

    @Override
    public void updatePhoneNumberByPatient(String dni, String newPhoneNumber) throws PatientNotExistsException {
       Optional<PatientEntity> patientEntityOptional = PatientEntity.findByIdOptional(dni);
       if (patientEntityOptional.isEmpty()) throw new PatientNotExistsException();
       PatientEntity patientEntity = patientEntityOptional.get();
       patientEntity.phone = newPhoneNumber;
       PatientEntity.update(patientEntity);
    }

    @Override
    public PatientEntity savePatient(PatientEntity patient) {

        LocalDateTime dateTimeNow = LocalDateTime.now();
        patient.statusPatient = StatusPatient.ACTIVE;
        patient.dateRegister = dateTimeNow;
        patient.password = generatePasswordKey(patient);
        PatientEntity.persist(patient);
        return patient;
    }

    @Override
    public boolean deletePatientById(String dni) throws PatientNotExistsException {
        return PatientEntity.deleteById(dni);
    }


    private String generatePasswordKey(PatientEntity patient){

        String ramdomChars = generateRandomString(10);
        String dniSplit = patient.dni.substring(2,6);
        String lasCharName = patient.name.substring(patient.name.length()-1).toUpperCase();


        return ramdomChars + dniSplit + lasCharName;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, characters.length())
                .mapToObj(i -> String.valueOf(characters.charAt(i)))
                .collect(Collectors.joining());
    }
}
