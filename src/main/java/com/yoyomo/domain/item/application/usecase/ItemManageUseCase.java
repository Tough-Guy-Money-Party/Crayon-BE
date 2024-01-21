package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;

public interface ItemManageUseCase {
    void create(String formId, ItemRequest request);

    void update(String formId, String itemId, ItemRequest request);

    void delete(String formId, String itemId);

    ItemResponse get(String formId, String itemId);
}
