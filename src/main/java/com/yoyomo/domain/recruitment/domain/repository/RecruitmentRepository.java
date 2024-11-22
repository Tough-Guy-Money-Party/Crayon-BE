package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RecruitmentRepository extends JpaRepository<Recruitment, UUID> {

    Page<Recruitment> findAllByClub(Club club, Pageable pageable);

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
}
