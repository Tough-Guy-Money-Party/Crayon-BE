package com.yoyomo.domain.application.domain.repository.mongo;

import com.yoyomo.domain.application.domain.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AnswerRepository extends MongoRepository<Answer, String> {

    Optional<Answer> findByApplicationId(String applicationId);
}
