package com.klashz;

import com.klashz.dto.MedicalHistoryEntityDtoRequest;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IMedicalHistoryService {

    MedicalHistoryEntity createMedicalHistory(ObjectId medicalId,MedicalHistoryEntityDtoRequest medicalHistoryEntity);
    Optional<MedicalHistoryEntity> getMedicalHistory(ObjectId idMedicalHistory);
    List<MedicalHistoryEntity> getAllMedicalHistory();
    List<MedicalHistoryEntity> getMedicalHistoryByPatientId(String patientId);
    List<MedicalHistoryEntity> getMedicalHistoryByMedicalId(ObjectId idMedical);
    void updateMedicalHistory(ObjectId idMedicalHistory,MedicalHistoryEntity medicalHistoryEntity);
    boolean deleteMedicalHistory(ObjectId idMedicalHistory);
    List<MedicalHistoryEntity> getMedicalHistoryByDiagnosisByPatientId(String patientId,String diagnosis);


}
