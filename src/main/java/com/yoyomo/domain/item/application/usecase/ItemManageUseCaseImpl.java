package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
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

    @Override
    public List<Item> create(List<ItemRequest> request) {
        return request.stream()
                .map(itemFactory::createItem)
                .toList();
    }
}
