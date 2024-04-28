package com.yoyomo.domain.template.domain.repository;

import com.yoyomo.domain.template.domain.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends MongoRepository<Template, String> {
    Optional<Template> findByIdAndDeletedAtIsNull(String id);

    List<Template> findAllByClubIdAndDeletedAtIsNull(String clubId);
}
