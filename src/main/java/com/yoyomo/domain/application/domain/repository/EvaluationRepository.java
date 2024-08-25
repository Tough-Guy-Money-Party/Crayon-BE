package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByIdAndDeletedAtIsNull(Long id);
}
