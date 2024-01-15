package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.form.domain.service.FormUpdateService;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemManageUseCaseImpl implements ItemManageUseCase {
    private final ItemFactory itemFactory;
    private final FormUpdateService formUpdateService;

    @Override
    public void create(String formId, ItemRequest request) {
        Item item = itemFactory.createItem(request);
        formUpdateService.addItem(formId, item);
    }

    @Override
    public void update(String formId, String itemId, ItemRequest request) {
        Item item = itemFactory.createItem(request);
        formUpdateService.updateItem(formId, itemId, item);
    }
}
