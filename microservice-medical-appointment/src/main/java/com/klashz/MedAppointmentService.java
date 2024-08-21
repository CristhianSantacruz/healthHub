package com.klashz;

import com.klashz.dto.MedAppointmentDtoRequest;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MedAppointmentService implements IMedAppointmentService {
    @Override
    public void createMedicalAppointment(MedAppointmentDtoRequest appointmentDto) {
        // "YYYY-MM-DD"
        LocalDate localDate = LocalDate.parse(appointmentDto.date());
        LocalTime time = LocalTime.parse(appointmentDto.hour());

        MedAppointment medAppointment = new MedAppointment(localDate,time,MedicalAppointmentStatus.Scheduled,appointmentDto.reason(),appointmentDto.idMedical(),appointmentDto.idPatient(),appointmentDto.consultationRoom());
        MedAppointment.persist(medAppointment);

    }

    @Override
    public Optional<MedAppointment> getMedAppointmentById(Long id) {
        return MedAppointment.findByIdOptional(id);
    }

    @Override
    public List<MedAppointment> getMedAppointmentsByPatientId(Long patientId) {

        return MedAppointment.list("patientId",patientId);
    }

    @Override
    public List<MedAppointment> getMedAppointmentsByDoctorId(Long idMedical) {
        return MedAppointment.list("idMedical",idMedical);
    }

    @Override
    public List<MedAppointment> getMedAppointmentsByStatus(String status) {
        return MedAppointment.list("status",status);
    }

    @Override
    public void updateMedicalAppointment(Long id, MedAppointment updatedAppointment) {
        Optional<MedAppointment> medAppointment = MedAppointment.findByIdOptional(id);
        if(medAppointment.isPresent()) {
            MedAppointment newDataMedAppointment = medAppointment.get();
            newDataMedAppointment.date = updatedAppointment.date;
            newDataMedAppointment.hour = updatedAppointment.hour;
            newDataMedAppointment.status = updatedAppointment.status;
            newDataMedAppointment.reason = updatedAppointment.reason;
            newDataMedAppointment.idMedical = updatedAppointment.idMedical;
            newDataMedAppointment.idPatient = updatedAppointment.idPatient;
            newDataMedAppointment.consultationRoom = updatedAppointment.consultationRoom;
            MedAppointment.update(newDataMedAppointment);
        }
    }

    @Override
    public boolean deleteMedicalAppointment(Long id) {
        return MedAppointment.deleteById(id);
    }

    @Override
    public boolean completedMedicalAppointment(Long id) {
        // buscar la consulta, cambiar el dato , y guardar la consulta
        Optional<MedAppointment> medAppointmentOptional = MedAppointment.findByIdOptional(id);
        if(medAppointmentOptional.isPresent()) {
            MedAppointment medAppointment = medAppointmentOptional.get();
            medAppointment.status = MedicalAppointmentStatus.Completed;
            medAppointment.update();
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelMedicalAppointment(Long id) {
        Optional<MedAppointment> medAppointmentOptional = MedAppointment.findByIdOptional(id);
        if(medAppointmentOptional.isPresent()) {
            MedAppointment medAppointment = medAppointmentOptional.get();
            medAppointment.status = MedicalAppointmentStatus.Canceled;
            medAppointment.update();
            return true;
        }
        return false;
    }
}
