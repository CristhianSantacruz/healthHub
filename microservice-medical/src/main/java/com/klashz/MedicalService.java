package com.klashz;

import com.klashz.client.MedicalHistoryService;
import com.klashz.client.PatientService;
import com.klashz.dto.MedicalHistoryDto;
import com.klashz.dto.MedicalHistoryDtoRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class MedicalService implements IMedicalService{

    @Inject
     MedicalRepository medicalRepository;

    @Inject
    @RestClient
    MedicalHistoryService medicalHistoryService;

    @Inject
    @RestClient
    PatientService patientService;

    @Override
    public Optional<MedicalEntity> getMedicalByIdWithMedicalHistory(ObjectId id) {
        Optional<MedicalEntity> medicalEntityOptional =  medicalRepository.findByIdOptional(id);
        if(medicalEntityOptional.isEmpty()){throw  new RuntimeException("No existe este medico");}
        MedicalEntity medicalEntity = medicalEntityOptional.get();
        List<MedicalHistoryDto> medicalHistories = medicalHistoryService.getMedicalHistoryByMedicalId(medicalEntity.id);

        medicalEntity.medicalHistoryOwnerList(medicalHistories.stream()
                .peek(medicalHistoryDto -> medicalHistoryDto.setPatientDto(patientService.getPatientByDni(medicalHistoryDto.getPatientId()).orElse(null)))
                .toList());

        return Optional.of(medicalEntity);
    }

    @Override
    public void saveMedical(MedicalEntity medicalEntity) {
        medicalEntity.qualifications = new ArrayList<>();
        medicalEntity.medicalHistoryOwnerList = new ArrayList<>();
        medicalRepository.persist(medicalEntity);
    }

    @Override
    public Optional<MedicalEntity> getMedicalById(ObjectId id) {
        return medicalRepository.findByIdOptional(id);
    }

    @Override
    public Optional<MedicalEntity> getMedicalByEmail(String email) {
        return medicalRepository.find("email",email).firstResultOptional();
    }

    @Override
    public List<MedicalEntity> getAllMedicals() {
        return medicalRepository.listAll();
    }

    @Override
    public void updateMedical(ObjectId id, MedicalEntity medicalEntity) {
        Optional<MedicalEntity> medicalEntityOptional =medicalRepository.findByIdOptional(id);
        if(medicalEntityOptional.isEmpty()) throw new IllegalArgumentException("Error");
        MedicalEntity medicalEntityToUpdate = medicalEntityOptional.get();
        medicalEntityToUpdate.name = medicalEntity.name;
        medicalEntityToUpdate.qualifications = medicalEntity.qualifications;
        medicalEntityToUpdate.email = medicalEntity.email;
        medicalEntityToUpdate.specialization = medicalEntity.specialization;
        medicalEntityToUpdate.department = medicalEntity.department;
        medicalEntityToUpdate.medicalHistoryOwnerList = medicalEntity.medicalHistoryOwnerList;

        medicalRepository.update(medicalEntityToUpdate);

    }

    @Override
    public boolean addQualificationToMedical(ObjectId medicalId,String qualifications) {
        Optional<MedicalEntity> medicalEntityOptional = medicalRepository.findByIdOptional(medicalId);
        if(medicalEntityOptional.isEmpty()){
            return false;
        }
        MedicalEntity medicalEntity = medicalEntityOptional.get();
        medicalEntity.qualifications.add(qualifications);
        medicalRepository.update(medicalEntity);
        return true;
    }

    @Override
    public boolean deleteMedical(ObjectId id) {
        return medicalRepository.deleteById(id);
    }

    @Override
    public List<MedicalEntity> searchMedicalBySpecialization(String specialization) {
        return medicalRepository.list("specialization",specialization);
    }

    @Override
    public List<MedicalEntity> searchMedicalByDepartment(String department) {
        return medicalRepository.list("department",department);
    }

    @Override
    public List<MedicalEntity> getMedicalByQualification(String qualification) {
        return  medicalRepository.listAll().stream()
                .filter(medicalEntity ->  medicalEntity != null &&  medicalEntity.qualifications.contains(qualification)).toList();
}



    @Override
    public URI createMedicalHistory(ObjectId medicalId, MedicalHistoryDtoRequest medicalHistoryDto) {
        URI uri = medicalHistoryService.createMedicalHistory(medicalId,medicalHistoryDto);
        return  uri;
    }
}
