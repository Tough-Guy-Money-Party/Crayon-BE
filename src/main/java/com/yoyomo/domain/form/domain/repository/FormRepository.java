package com.yoyomo.domain.form.domain.repository;

import com.yoyomo.domain.form.domain.entity.Form;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormRepository extends MongoRepository<Form, String> {
}
