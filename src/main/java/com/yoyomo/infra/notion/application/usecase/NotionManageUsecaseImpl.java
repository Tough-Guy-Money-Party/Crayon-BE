package com.yoyomo.infra.notion.application.usecase;


import com.yoyomo.infra.notion.service.NotionGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotionManageUsecaseImpl implements NotionManageUsecase{
    private final NotionGetService notionGetService;

    @Override
    public Map<String, Object> getPage(String pageId){
        return notionGetService.getPageChunk(pageId);
    }
}
