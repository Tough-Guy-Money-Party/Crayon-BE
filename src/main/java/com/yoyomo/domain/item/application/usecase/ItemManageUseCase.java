package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;

public interface ItemManageUseCase {
    void create(String formId, ItemRequest request);

    void update(String formId, String itemId, ItemRequest request);
}
