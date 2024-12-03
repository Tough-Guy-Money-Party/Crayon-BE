package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findAllByApplicationId(UUID applicationId);
}
