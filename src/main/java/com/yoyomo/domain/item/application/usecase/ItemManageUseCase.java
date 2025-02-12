package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;

import java.util.List;

public interface ItemManageUseCase {
    List<Item> create(List<ItemRequest> request);
}
