package com.yoyomo.domain.form.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.form.application.dto.request.FormRequest;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.exception.FormNotFoundException;
import com.yoyomo.domain.shared.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Transactional
@RequiredArgsConstructor
public class FormUpdateService {
    private static final String ID = "id";
    private static final String DELETED_AT = "deletedAt";
    private final MongoTemplate mongoTemplate;

    public void update(String id, FormRequestDTO.Update dto) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update()
                .set("title", dto.title())
                .set("description", dto.description())
                .set("enabled", dto.enabled());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Form.class);
        checkIsDeleted(result);
    }

    public void update(String id, Boolean enabled) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update()
                .set("enabled", enabled);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Form.class);
        checkIsDeleted(result);
    }

    public void from(String id, FormRequest request) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = MapperUtil.mapToUpdate(request);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Form.class);
        checkIsDeleted(result);
    }

    public void delete(String id) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update().currentDate(DELETED_AT);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Form.class);
        checkIsDeleted(result);
    }

    private void checkIsDeleted(UpdateResult result) {
        if (result.getMatchedCount() == 0) {
            throw new FormNotFoundException();
        }
    }

}
