package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.Applicant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findAllByRecruitmentId(String recruitmentId, Pageable pageable);

    List<Application> findAllByRecruitmentIdAndApplicant_NameContaining(String recruitmentId, String name, Pageable pageable);

    List<Application> findAllByRecruitmentIdAndApplicationStage(String recruitmentId, int applicationStage);

    int countByRecruitmentId(String recruitmentId);

    int countByRecruitmentIdAndApplicationStageLessThan(String recruitmentId, int stage);

    int countByRecruitmentIdAndApplicationStageGreaterThanEqual(String recruitmentId, int stage);

    List<Application> findAllByApplicant(Applicant applicant, Pageable pageable);

    boolean existsByApplicantAndRecruitment_Id(Applicant applicant, String recruitmentId);

    Optional<Application> findByRecruitment_IdAndApplicant_NameAndApplicant_Phone(String recruitmentId, String name, String phone);
}
