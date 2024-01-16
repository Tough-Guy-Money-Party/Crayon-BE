package com.yoyomo.domain.form.domain.service;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FormUpdateService {
    private static final String ID = "id";
    private static final String DELETED_AT = "deletedAt";
    private final MongoTemplate mongoTemplate;

    public void from(String id, FormRequest request) {
        Query query = query(
                where(ID).is(id).and(DELETED_AT).isNull()
        );
        Update update = MapperUtil.mapToUpdate(request);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Form.class);
        checkIsDeleted(result);
    }

    public void updateItem(String formId, String itemId, Item item) {
        Form form = formGetService.find(formId);
        form.updateItem(itemId, item);
        formSaveService.save(form);
    }

    private void checkIsDeleted(UpdateResult result) {
        if (result.getMatchedCount() == 0) {
            throw new ClubNotFoundException();
        }
    }
}
