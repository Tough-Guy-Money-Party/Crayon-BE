package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    Page<Application> findAllByRecruitmentIdAndCurrentStage(String recruitmentId, Integer stage, Pageable pageable);

    List<Application> findAllByRecruitmentIdAndApplicant_NameContaining(String recruitmentId, String name, Pageable pageable);

    List<Application> findAllByRecruitmentIdAndCurrentStage(String recruitmentId, int currentStage);

    int countByRecruitmentId(String recruitmentId);

    int countByRecruitmentIdAndCurrentStageLessThan(String recruitmentId, int stage);

    int countByRecruitmentIdAndCurrentStageGreaterThanEqual(String recruitmentId, int stage);

    List<Application> findAllByApplicant(Applicant applicant, Pageable pageable);

    boolean existsByApplicantAndRecruitmentId(Applicant applicant, String recruitmentId);

    Optional<Application> findByRecruitmentIdAndApplicant_NameAndApplicant_PhoneAndApplicant_Email(String recruitmentId, String name, String phone, String email);
}
