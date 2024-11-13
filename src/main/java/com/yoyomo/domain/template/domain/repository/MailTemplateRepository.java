package com.yoyomo.domain.template.domain.repository;

import com.yoyomo.domain.template.domain.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, UUID> {
}
