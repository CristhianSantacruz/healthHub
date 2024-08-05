package com.klashz.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDateTime;

public record PatientDto(String dni,String name,String gender,String age) {
}
