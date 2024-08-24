package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
}
