package com.yoyomo.domain.application.domain.repository.mongo;

import com.yoyomo.domain.application.domain.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerRepository extends MongoRepository<Answer, String> {

    Optional<Answer> findByApplicationId(String applicationId);

    @Query("{ 'applicationId': { $in: ?0 } }")
    List<Answer> findAllByApplicationIds(List<UUID> applicationIds);
}
