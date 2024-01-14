package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

public interface ItemManageUseCase {
    void create(ItemRequest request);
}
