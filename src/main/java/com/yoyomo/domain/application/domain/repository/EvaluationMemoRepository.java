package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluationMemoRepository extends JpaRepository<EvaluationMemo, Long> {

    List<EvaluationMemo> findAllByProcessIdAndApplication(long processId, Application application);

    long deleteByIdAndManager(long memoId, User manager);

    Optional<EvaluationMemo> findByIdAndManager(long memoId, User manager);
}
