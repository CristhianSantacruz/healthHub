package com.klashz;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MedicalHistoryRepository implements PanacheMongoRepository<MedicalHistoryEntity> {
}
