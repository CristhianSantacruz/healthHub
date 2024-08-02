package com.klashz;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MongoEntity(collection = "patient")
public class PatientEntity extends PanacheMongoEntityBase {

    @BsonId
    @NotNull @NotBlank
    public String dni;
    @NotNull @NotBlank
    public String name;
    @NotNull @NotBlank @Email
    public String email;
    @NotNull @NotBlank
    public String gender;
    @NotNull @NotBlank
    public String age;
    @NotNull @NotBlank
    public String phone;
    @NotNull @NotBlank
    public String password;
    @NotNull @NotBlank
    public String address;
    @NotNull @NotBlank
    public String bloodType;
    @NotNull @NotBlank
    public String emergencyContact;
    public StatusPatient statusPatient;
    public LocalDateTime dateRegister;

}
