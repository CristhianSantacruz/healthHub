package com.klashz;

import com.klashz.dto.MedicalHistoryDto;
import com.klashz.dto.MedicalHistoryDtoRequest;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.types.ObjectId;

import java.net.URI;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

public interface IMedicalService {


    void saveMedical(MedicalEntity medicalEntity);
    Optional<MedicalEntity> getMedicalByIdWithMedicalHistory(ObjectId id);
    Optional<MedicalEntity> getMedicalById(ObjectId id);
    Optional<MedicalEntity> getMedicalByEmail(String email);
    List<MedicalEntity> getAllMedicals();
    void updateMedical(ObjectId id , MedicalEntity medicalEntity);
    boolean addQualificationToMedical(ObjectId medicalId,String qualification);
    boolean deleteMedical(ObjectId id);
    List<MedicalEntity> searchMedicalBySpecialization(String specialization);
    List<MedicalEntity> searchMedicalByDepartment(String department);
    List<MedicalEntity> getMedicalByQualification(String qualification);

    //clients

    URI createMedicalHistory(ObjectId medical, MedicalHistoryDtoRequest medicalHistoryDto);

}
