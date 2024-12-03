package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationMemoRepository extends JpaRepository<EvaluationMemo, Long> {
}
