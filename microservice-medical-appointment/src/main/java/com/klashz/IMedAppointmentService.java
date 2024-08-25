package com.klashz;
import com.klashz.dto.AcceptedPatientMedAppointmentDto;
import com.klashz.dto.MedAppointmentDtoRequest;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IMedAppointmentService {

    MedAppointment createMedicalAppointment(MedAppointmentDtoRequest appointment);
    Optional<MedAppointment> getMedAppointmentById(ObjectId id);
    List<MedAppointment> getAllMedAppointments();
    List<MedAppointment> getMedAppointmentsByPatientId(String patientId);
    List<MedAppointment> getMedAppointmentsByDoctorId(ObjectId doctorId);
    List<MedAppointment> getMedAppointmentsByStatus(String status);

    boolean updateMedicalAppointment(ObjectId id, MedAppointment updatedAppointment);
    boolean deleteMedicalAppointment(ObjectId id);
    boolean completedMedicalAppointment(ObjectId id);
    boolean cancelMedicalAppointment(ObjectId id);
    boolean acceptedMedicalAppointment(ObjectId id, AcceptedPatientMedAppointmentDto acceptedPatientMedAppointmentDto);
    long deleteAllMedAppointments();
}
