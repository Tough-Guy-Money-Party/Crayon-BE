package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
