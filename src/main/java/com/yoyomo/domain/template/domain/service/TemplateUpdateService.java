package com.yoyomo.domain.template.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.shared.util.MapperUtil;
import com.yoyomo.domain.template.application.dto.req.TemplateRequest;
import com.yoyomo.domain.template.domain.entity.Template;
import com.yoyomo.domain.template.exception.TemplateNotFoundException;
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
public class TemplateUpdateService {
    private static final String ID = "id";
    private static final String DELETED_AT = "deletedAt";
    private final MongoTemplate mongoTemplate;

    public void update(String id, String name, String passText, String failText) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update()
                .set("name", name)
                .set("passText", passText)
                .set("failText", failText);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Template.class);
        checkIsDeleted(result);
    }

    public void from(String id, TemplateRequest request) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = MapperUtil.mapToUpdate(request);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Template.class);
        checkIsDeleted(result);
    }

    public void delete(String id) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update().currentDate(DELETED_AT);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Template.class);
        checkIsDeleted(result);
    }

    private void checkIsDeleted(UpdateResult result) {
        if (result.getMatchedCount() == 0) {
            throw new TemplateNotFoundException();
        }
    }

}
