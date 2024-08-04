package com.klashz;

import com.klashz.dto.MedicalHistoryEntityDtoRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MedicalHistoryService implements IMedicalHistoryService {

    @Inject
    MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public MedicalHistoryEntity createMedicalHistory(ObjectId medicalId,MedicalHistoryEntityDtoRequest medicalHistoryEntityDtoRequest) {
        LocalDateTime now = LocalDateTime.now();
        List<String> medications = Arrays.stream(medicalHistoryEntityDtoRequest.medications().split(",")).toList();
        String dataDateNextAppointment[] = medicalHistoryEntityDtoRequest.nextAppointment().split("/");
        LocalDate dateNextAppointment = LocalDate.of(now.getYear(),Integer.parseInt(dataDateNextAppointment[1]),Integer.parseInt(dataDateNextAppointment[2]));
        MedicalHistoryEntity medicalHistoryEntity = new MedicalHistoryEntity(
                medicalId,
                medicalHistoryEntityDtoRequest.patientId(),
                now,
                medicalHistoryEntityDtoRequest.diagnosis(),
                medicalHistoryEntityDtoRequest.treatment(),
                medications,
                medicalHistoryEntityDtoRequest.notes(),
                dateNextAppointment
        );

        medicalHistoryRepository.persist(medicalHistoryEntity);
        return medicalHistoryEntity;
    }

    @Override
    public Optional<MedicalHistoryEntity> getMedicalHistory(ObjectId idMedicalHistory) {
        return medicalHistoryRepository.findByIdOptional(idMedicalHistory);
    }

    @Override
    public List<MedicalHistoryEntity> getAllMedicalHistory() {
        return medicalHistoryRepository.listAll();
    }

    @Override
    public List<MedicalHistoryEntity> getMedicalHistoryByPatientId(String patientId) {
        return medicalHistoryRepository.list("patientId", patientId);
    }

    @Override
    public List<MedicalHistoryEntity> getMedicalHistoryByMedicalId(ObjectId idMedical) {
        return medicalHistoryRepository.list("medicalId", idMedical);
    }

    @Override
    public void updateMedicalHistory(ObjectId idMedicalHistory, MedicalHistoryEntity newDataMedicalHistory) {
        Optional<MedicalHistoryEntity> medicalHistoryEntityOptional = medicalHistoryRepository.findByIdOptional(idMedicalHistory);
        if (medicalHistoryEntityOptional.isEmpty()) {throw new RuntimeException("Error: Medical History Not Found");}
        MedicalHistoryEntity medicalHistoryUpdate = medicalHistoryEntityOptional.get();
        LocalDateTime now = LocalDateTime.now();
        medicalHistoryUpdate.setDateCreation(now);
        medicalHistoryUpdate.setDiagnosis(newDataMedicalHistory.getDiagnosis());
        medicalHistoryUpdate.setTreatment(newDataMedicalHistory.getTreatment());
        medicalHistoryUpdate.setNotes(newDataMedicalHistory.getNotes());
        medicalHistoryUpdate.setNextAppointment(newDataMedicalHistory.getNextAppointment());

        medicalHistoryRepository.update(medicalHistoryUpdate);
    }

    @Override
    public boolean deleteMedicalHistory(ObjectId idMedicalHistory) {
        return medicalHistoryRepository.deleteById(idMedicalHistory);
    }

    @Override
    public List<MedicalHistoryEntity> getMedicalHistoryByDiagnosisByPatientId(String patientId, String diagnosis) {
        return medicalHistoryRepository.list("patientId",patientId)
                .stream()
                .filter(medicalHistoryEntity -> medicalHistoryEntity.getDiagnosis().equals(diagnosis)).toList();
    }


}
