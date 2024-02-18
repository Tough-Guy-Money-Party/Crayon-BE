package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.application.mapper.ItemMapper;
import com.yoyomo.domain.item.domain.entity.*;
import com.yoyomo.domain.item.domain.service.ItemUpdateService;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import com.yoyomo.domain.item.exception.InvalidItemException;
import com.yoyomo.domain.item.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemManageUseCaseImpl implements ItemManageUseCase {
    private final ItemFactory itemFactory;
    private final ItemUpdateService itemUpdateService;
    private final ItemMapper itemMapper;

    @Override
    public List<Item> create(List<ItemRequest> request) {
        return request.stream()
                .map(itemFactory::createItem)
                .toList();
    }

    @Override
    public void update(String formId, List<ItemRequest> requests) {
        List<Item> items = requests.stream()
                .map(itemFactory::createItem)
                .toList();
        itemUpdateService.updateItem(formId, items);
    }

    @Override
    public List<ItemResponse> get(Form form) {
        List<Item> items = form.getItems();

        return items.stream()
                .map(this::getItemResponse)
                .toList();
    }

    public ItemResponse getItemResponse(Item item) {
        if (item instanceof Text) {
            return itemMapper.mapToTextResponse((Text) item);
        }else if (item instanceof Select) {
            return itemMapper.mapToSelectResponse((Select) item);
        }else if (item instanceof Stage ){
            return itemMapper.mapToStageResponse((Stage) item);
        }else if (item instanceof Date ){
            return itemMapper.mapToDateResponse((Date) item);
        }else if (item instanceof File ){
            return itemMapper.mapToFileResponse((File) item);
        }
        throw new InvalidItemException();
    }
}
