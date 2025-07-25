package com.yoyomo.domain.item.application.usecase;

import java.util.List;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;

public interface ItemManageUseCase {
	List<Item> create(List<ItemRequest> request);
}
