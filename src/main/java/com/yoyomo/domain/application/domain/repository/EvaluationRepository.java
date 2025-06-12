package com.yoyomo.domain.application.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.user.domain.entity.User;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

	List<Evaluation> findAllByApplicationId(UUID applicationId);

	@EntityGraph(attributePaths = "manager")
	List<Evaluation> findAllByApplication(Application application);

	boolean existsByManagerAndApplication(User manager, Application application);

	@Modifying
	@Query("DELETE FROM Evaluation e WHERE e.application.recruitmentId = :recruitmentId")
	void deleteByRecruitmentId(@Param("recruitmentId") UUID recruitmentId);
}
