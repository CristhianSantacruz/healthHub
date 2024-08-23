package com.klashz;

import com.klashz.dto.MedAppointmentDtoRequest;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MedAppointmentService implements IMedAppointmentService {
    @Override
    public MedAppointment createMedicalAppointment(MedAppointmentDtoRequest appointmentDto) {
        // "YYYY-MM-DD"
        LocalDate localDate = LocalDate.parse(appointmentDto.date());
        //"10:15:45"
        LocalTime time = LocalTime.parse(appointmentDto.hour());
        MedAppointment medAppointment = new MedAppointment();
        medAppointment.date = localDate;
        medAppointment.hour = time;
        medAppointment.status = MedicalAppointmentStatus.Scheduled;
        medAppointment.reason = appointmentDto.reason();
        medAppointment.idMedical = appointmentDto.idMedical();
        medAppointment.idPatient = appointmentDto.idPatient();
        medAppointment.consultationRoom = appointmentDto.consultationRoom();
        medAppointment.persist();
        return medAppointment;

    }

    @Override
    public Optional<MedAppointment> getMedAppointmentById(ObjectId id) {
        return MedAppointment.findByIdOptional(id);
    }

    @Override
    public List<MedAppointment> getAllMedAppointments() {
        return MedAppointment.listAll();
    }

    @Override
    public List<MedAppointment> getMedAppointmentsByPatientId(String patientId) {

        return MedAppointment.list("patientId",patientId);
    }

    @Override
    public List<MedAppointment> getMedAppointmentsByDoctorId(ObjectId idMedical) {
        return MedAppointment.list("idMedical",idMedical);
    }

    @Override
    public List<MedAppointment> getMedAppointmentsByStatus(String status) {
        MedicalAppointmentStatus medicalAppointmentStatus = MedicalAppointmentStatus.valueOf(status);
        return MedAppointment.list("status",medicalAppointmentStatus);
    }

    @Override
    public boolean updateMedicalAppointment(ObjectId id, MedAppointment updatedAppointment) {
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
            newDataMedAppointment.update();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMedicalAppointment(ObjectId id) {
        return MedAppointment.deleteById(id);
    }

    @Override
    public boolean completedMedicalAppointment(ObjectId id) {
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
    public boolean cancelMedicalAppointment(ObjectId id) {
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
