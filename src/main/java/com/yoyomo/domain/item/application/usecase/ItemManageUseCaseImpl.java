package com.yoyomo.domain.item.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;

import lombok.RequiredArgsConstructor;

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
