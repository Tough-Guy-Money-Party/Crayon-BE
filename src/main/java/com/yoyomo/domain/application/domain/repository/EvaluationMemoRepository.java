package com.yoyomo.domain.application.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.user.domain.entity.User;

import io.lettuce.core.dynamic.annotation.Param;

public interface EvaluationMemoRepository extends JpaRepository<EvaluationMemo, Long> {

	List<EvaluationMemo> findAllByApplication(Application application);

	@Modifying
	@Query("DELETE FROM EvaluationMemo em WHERE em.id = :memoId AND em.manager = :manager")
	int deleteByIdAndManager(long memoId, User manager);

	@Modifying
	@Query("UPDATE EvaluationMemo em SET em.memo = :memo WHERE em.id = :memoId AND em.manager = :manager")
	int updateByIdAndManager(String memo, long memoId, User manager);

	@Modifying
	@Query("DELETE FROM EvaluationMemo em WHERE em.application.recruitmentId = :recruitmentId")
	void deleteByRecruitmentId(@Param("recruitmentId") UUID recruitmentId);
}
