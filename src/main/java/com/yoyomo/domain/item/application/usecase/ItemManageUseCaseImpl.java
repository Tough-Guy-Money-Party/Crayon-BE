package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.form.domain.service.FormGetService;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.application.mapper.ItemMapper;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Select;
import com.yoyomo.domain.item.domain.entity.Text;
import com.yoyomo.domain.item.domain.service.ItemUpdateService;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemManageUseCaseImpl implements ItemManageUseCase {
    private final ItemFactory itemFactory;
    private final FormGetService formGetService;
    private final ItemUpdateService itemUpdateService;
    private final ItemMapper itemMapper;

    @Override
    public void create(String formId, ItemRequest request) {
        Item item = itemFactory.createItem(request);
        itemUpdateService.addItem(formId, item);
    }

    @Override
    public void update(String formId, String itemId, ItemRequest request) {
        Item item = itemFactory.createItem(request);
        itemUpdateService.updateItem(formId, itemId, item);
    }

    @Override
    public void delete(String formId, String itemId) {
        itemUpdateService.deleteItem(formId, itemId);
    }

    @Override
    public ItemResponse get(String formId, String itemId) {
        Form form = formGetService.find(formId);
        Item item = form.getItem(itemId);
        if (item instanceof Text) {
            return itemMapper.mapToTextResponse((Text) item);
        }
        return itemMapper.mapToSelectResponse((Select) item);
    }
}
