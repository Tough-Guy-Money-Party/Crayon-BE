package com.yoyomo.infra.notion.application.usecase;

import java.util.Map;

public interface NotionManageUsecase {
	public Map<String, Object> getPage(String pageId);
}
