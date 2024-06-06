package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.req.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.req.AssessmentRequest;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Assessment;
import com.yoyomo.domain.interview.domain.entity.Interview;
import com.yoyomo.domain.shared.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUpdateService {
    private static final String ID = "id";
    private final MongoTemplate mongoTemplate;

    public void from(String id, ApplicationRequest request) {
        Query query = query(where(ID).is(id));
        Update update = MapperUtil.mapToUpdate(request);
        mongoTemplate.updateFirst(query, update, Application.class);
    }

    public void from(String id, ApplicationStatusRequest request) {
        Query query = query(where(ID).is(id));
        Update update = MapperUtil.mapToUpdate(request);
        mongoTemplate.updateFirst(query, update, Application.class);
    }

    public void from(String id, Interview interview) {
        Query query = query(where(ID).is(id));
        Update update = MapperUtil.mapToUpdate(interview);
        mongoTemplate.updateFirst(query, update, Application.class);
    }

    public void from(String id, AssessmentRequest request) {
        Query query = query(where(ID).is(id));

        Assessment.AssessmentBuilder assessmentBuilder = Assessment.builder()
                .managerId(request.managerId())
                .managerName(request.managerName())
                .createdAt(LocalDateTime.now());

        if (request.assessmentRating() != null) {
            assessmentBuilder.assessmentRating(request.assessmentRating());
        }

        if (request.assessmentText() != null) {
            assessmentBuilder.assessmentText(request.assessmentText());
        }
        Assessment assessment = assessmentBuilder.build();

        Update update = new Update().addToSet("assessments", assessment);
        mongoTemplate.updateFirst(query, update, Application.class);
    }
}
