package com.yoyomo.infra.notion.application.usecase;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yoyomo.infra.notion.service.NotionGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotionManageUsecaseImpl implements NotionManageUsecase {
	private final NotionGetService notionGetService;

	@Override
	public Map<String, Object> getPage(String pageId) {
		return notionGetService.getPageChunk(pageId);
	}
}
