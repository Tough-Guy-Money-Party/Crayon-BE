package com.yoyomo.domain.template.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yoyomo.domain.template.domain.entity.MailTemplate;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, UUID> {

	Page<MailTemplate> findAllByClubId(UUID clubId, Pageable pageable);
}
