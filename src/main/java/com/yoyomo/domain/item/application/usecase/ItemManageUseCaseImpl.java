package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.application.mapper.ItemMapper;
import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Date;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.entity.Score;
import com.yoyomo.domain.item.domain.entity.Select;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemManageUseCaseImpl implements ItemManageUseCase {

    private final ItemFactory itemFactory;
    private final ItemMapper itemMapper;

    @Override
    public List<Item> create(List<ItemRequest> request) {
        return request.stream()
                .map(itemFactory::createItem)
                .toList();
    }

    @Override
    public List<ItemResponse> get(Form form) {
        List<Item> items = form.getItems();

        return items.stream()
                .map(this::getItemResponse)
                .toList();
    }

    private ItemResponse getItemResponse(Item item) {
        if (item instanceof Select) {
            return itemMapper.mapToSelectResponse((Select) item);
        }
        if (item instanceof Score) {
            return itemMapper.mapToScoreResponse((Score) item);
        }
        if (item instanceof Date) {
            return itemMapper.mapToDateResponse((Date) item);
        }
        if (item instanceof Answer) {
            return itemMapper.mapToAnswerResponse((Answer) item);
        }

        return itemMapper.mapToItemResponse(item);
    }
}
