package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Item;
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
    private static final String ITEMS = "items";
    private final MongoTemplate mongoTemplate;

    public void addItem(String id, Item item) {
        Query query = query(
                where(ID).is(id)
        );
        Update update = new Update().push(ITEMS).value(item);
        mongoTemplate.updateFirst(query, update, Form.class);
    }
}
