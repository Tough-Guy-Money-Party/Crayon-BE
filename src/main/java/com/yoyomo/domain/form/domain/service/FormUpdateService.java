package com.yoyomo.domain.form.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.form.application.dto.request.FormRequestDTO;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.exception.FormNotFoundException;
import com.yoyomo.domain.form.exception.FormUnmodifiableException;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
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
public class FormUpdateService {
    private static final String ID = "id";
    private static final String DELETED_AT = "deletedAt";
    
    private final MongoTemplate mongoTemplate;
    private final RecruitmentRepository recruitmentRepository;
    private final ItemFactory itemFactory;

    public void update(String id, FormRequestDTO.Update dto) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update()
                .set("title", dto.title())
                .set("description", dto.description());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Form.class);
        checkIsDeleted(result);
    }

    public void update(Form form, String title, String description, List<ItemRequest> rawItems) {
        long linkedRecruitmentCount = recruitmentRepository.countByFormId(form.getId());
        if (linkedRecruitmentCount != 0) {
            throw new FormUnmodifiableException(); // todo 연결이 기준인지, 활성화가 기준인지 파악 후 수정
        }

        List<Item> items = rawItems.stream()
                .map(itemFactory::createItem)
                .toList();
        form.update(title, description, items);
    }

    public void update(String id, List<String> recruitmentIds) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = new Update()
                .set("recruitmentIds", recruitmentIds);
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
