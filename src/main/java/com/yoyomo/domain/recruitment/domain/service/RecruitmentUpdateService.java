package com.yoyomo.domain.recruitment.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentModifyRequest;
import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.domain.shared.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentUpdateService {
    private static final String ID = "id";
    private static final String DELETED_AT = "deletedAt";
    private final MongoTemplate mongoTemplate;

    public void from(String id, RecruitmentModifyRequest request) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = MapperUtil.mapToUpdate(request);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recruitment.class);
        checkIsDeleted(result);
    }

    public void from(String id, Form form) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = MapperUtil.mapToUpdate(form);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recruitment.class);
        checkIsDeleted(result);
    }

    public void from(String id, List<Process> processes) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update()
                .set("processes", processes);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recruitment.class);
        checkIsDeleted(result);
    }

    public void delete(String id) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update().currentDate(DELETED_AT);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Recruitment.class);
        checkIsDeleted(result);
    }

    private void checkIsDeleted(UpdateResult result) {
        if (result.getMatchedCount() == 0) {
            throw new RecruitmentNotFoundException();
        }
    }
}
