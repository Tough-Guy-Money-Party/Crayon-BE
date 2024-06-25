package com.yoyomo.domain.form.domain.repository;

import com.yoyomo.domain.form.domain.entity.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends MongoRepository<Form, String> {
    Optional<Form> findByIdAndDeletedAtIsNull(String id);

    List<Form> findAllByClubIdAndDeletedAtIsNull(String clubId);

    List<Form> findByClubIdAndTitleRegexOrClubIdAndDescriptionRegex(String clubId, String titleRegex, String clubId2, String descriptionRegex);
}
