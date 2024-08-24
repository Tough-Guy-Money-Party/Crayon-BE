package com.yoyomo.domain.application.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.exception.AnswerNotFoundException;
import com.yoyomo.domain.item.domain.entity.Item;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerUpdateService {

    private static final String ID = "id";
    private final MongoTemplate mongoTemplate;

    public void from(String id, List<Item> items) {
        Query query = query(where(ID).is(id));

        Update update = new Update()
                .set("items", items);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Answer.class);
        checkIsDeleted(result);
    }

    private void checkIsDeleted(UpdateResult result) {
        if (result.getMatchedCount() == 0) {
            throw new AnswerNotFoundException();
        }
    }
}
