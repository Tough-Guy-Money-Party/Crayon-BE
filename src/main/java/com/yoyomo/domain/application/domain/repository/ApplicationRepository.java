package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findAllByRecruitmentId(String recruitmentId);

    List<Application> findAllByApplicant(Applicant applicant);

    boolean existsByApplicantAndRecruitment_Id(Applicant applicant, String recruitmentId);
}
