package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.domain.entity.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yoyomo.domain.shared.util.MapperUtil.mapToUpdate;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubUpdateServiceImpl implements ClubUpdateService {
    private final MongoTemplate mongoTemplate;

    public void from(String id, ClubRequest request) {
        Query query = query(where(ID).is(id));
        Update update = mapToUpdate(request);
        mongoTemplate.updateFirst(query, update, Club.class);
    }

    public void delete(String id) {
        Query query = query(where(ID).is(id));
        Update update = new Update().currentDate(DELETED_AT);
        mongoTemplate.updateFirst(query, update, Club.class);
    }
}
