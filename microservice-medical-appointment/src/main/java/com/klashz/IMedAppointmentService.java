package com.klashz;
import com.klashz.dto.MedAppointmentDtoRequest;

import java.util.List;
import java.util.Optional;

public interface IMedAppointmentService {

    void createMedicalAppointment(MedAppointmentDtoRequest appointment);
   Optional<MedAppointment> getMedAppointmentById(Long id);
    List<MedAppointment> getMedAppointmentsByPatientId(Long patientId);
    List<MedAppointment> getMedAppointmentsByDoctorId(Long doctorId);
    List<MedAppointment> getMedAppointmentsByStatus(String status);

    void updateMedicalAppointment(Long id, MedAppointment updatedAppointment);
    boolean deleteMedicalAppointment(Long id);
    boolean completedMedicalAppointment(Long id);
    boolean cancelMedicalAppointment(Long id);

}
