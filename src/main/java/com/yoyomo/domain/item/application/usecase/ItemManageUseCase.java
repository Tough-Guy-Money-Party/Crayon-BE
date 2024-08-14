package com.yoyomo.domain.item.application.usecase;

import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.application.dto.res.ItemResponse;
import com.yoyomo.domain.item.domain.entity.Item;

import java.util.List;

public interface ItemManageUseCase {
    List<Item> create(List<ItemRequest> request);

    void update(String formId, List<ItemRequest> requests);

    List<ItemResponse> get(Form form);
}
