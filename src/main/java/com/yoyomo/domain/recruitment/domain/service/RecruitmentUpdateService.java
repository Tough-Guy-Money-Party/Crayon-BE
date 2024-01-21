package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.application.dto.req.RecruitmentRequest;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
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
public class RecruitmentUpdateService {
    private static final String ID = "id";
    private static final String DELETED_AT = "deletedAt";
    private final MongoTemplate mongoTemplate;

    public void from(String id, RecruitmentRequest request) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = MapperUtil.mapToUpdate(request);
        mongoTemplate.updateFirst(query, update, Recruitment.class);
    }
}
