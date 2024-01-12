package com.yoyomo.domain.club.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
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
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = mapToUpdate(request);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Club.class);
        checkIsDeleted(result);
    }

    public void delete(String id) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update().currentDate(DELETED_AT);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Club.class);
        checkIsDeleted(result);
    }

    private void checkIsDeleted(UpdateResult result) {
        if (result.getMatchedCount() == 0) {
            throw new ClubNotFoundException();
        }
    }
}
