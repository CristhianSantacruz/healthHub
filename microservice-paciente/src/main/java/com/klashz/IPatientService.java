package com.klashz;

import com.klashz.dto.PatientReservedAppointment;
import com.klashz.exceptions.PatientNotExistsException;
import org.bson.types.ObjectId;

import java.util.Optional;
import java.util.List;
public interface IPatientService {

    Optional<PatientEntity> getPatientByDni(String dni) throws PatientNotExistsException;
    Optional<PatientEntity> getPatientByEmail(String email) throws PatientNotExistsException;
    List<PatientEntity> getPatientsActive();
    List<PatientEntity> getPatientForHisBloodType(String bloodType);
    List<PatientEntity> getAllPatients();
    void updatePatient(String dni,PatientEntity patient) throws PatientNotExistsException;
    void updatePhoneNumberByPatient(String dni,String newPhoneNumber) throws PatientNotExistsException;
    PatientEntity savePatient(PatientEntity patient);
    boolean reservedMedAppointment(ObjectId id , PatientReservedAppointment patientReservedAppointment);
    boolean deletePatientById(String dni) throws PatientNotExistsException;

}
