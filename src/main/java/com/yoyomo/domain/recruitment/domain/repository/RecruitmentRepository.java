package com.yoyomo.domain.recruitment.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {

	Page<Recruitment> findAllByClubOrderByCreatedAtDesc(Club club, Pageable pageable);

	long countByFormId(String formId);

	@Query("""
		SELECT new com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment(
			r.formId,
			r.id
		)
		FROM Recruitment r
		WHERE r.formId IN :formIds
		""")
	List<LinkedRecruitment> findByForms(List<String> formIds);

	List<Recruitment> findAllByFormId(String formId);

	@Modifying
	@Query("UPDATE Recruitment r SET r.totalApplicantsCount = r.totalApplicantsCount + 1 WHERE r.id = :recruitmentId")
	void increaseApplicantCount(UUID recruitmentId);

	@Modifying
	@Query("""
		UPDATE Recruitment r SET r.totalApplicantsCount = r.totalApplicantsCount + :count WHERE r.id = :recruitmentId
		""")
	void increaseApplicantCount(UUID recruitmentId, int count);

	@Modifying
	@Query("UPDATE Recruitment r SET r.totalApplicantsCount = r.totalApplicantsCount - 1 WHERE r.id = :recruitmentId")
	void decreaseApplicantCount(UUID recruitmentId);
}
