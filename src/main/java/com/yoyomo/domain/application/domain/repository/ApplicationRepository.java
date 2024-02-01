package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.SubmitStatus;
import com.yoyomo.domain.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findAllByRecruitmentId(String recruitmentId);

    List<Application> findAllByUser(User user);

    Optional<Application> findByUserAndRecruitmentIdAndSubmitStatus(User user, String recruitmentId, SubmitStatus submitStatus);

    boolean existsByUserAndRecruitment_Id(User user, String recruitmentId);
}
