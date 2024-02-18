package com.yoyomo.domain.user.domain.repository;

import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ApplicantRepository extends MongoRepository<Applicant, String> {
    Optional<Applicant> findByNameAndPhone(String name, String phone);
}

