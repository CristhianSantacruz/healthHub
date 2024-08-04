package com.klashz;

import com.klashz.dto.MedicalHistoryDto;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "medical")
public class MedicalEntity {

    public ObjectId id;
    @NotBlank @NotNull
    public String name;
    @NotBlank @NotNull
    public String specialization;
    @NotBlank @NotNull @Email
    public String email;
    public  List<String> qualifications;
    @NotBlank @NotNull
    public String department;

    //medical history owners
    public List<MedicalHistoryDto> medicalHistoryOwnerList;




}
