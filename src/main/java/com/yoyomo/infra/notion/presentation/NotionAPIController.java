package com.yoyomo.infra.notion.presentation;

import com.yoyomo.infra.notion.application.usecase.NotionManageUsecase;
import com.yoyomo.infra.notion.application.usecase.NotionManageUsecaseImpl;
import com.yoyomo.infra.notion.service.NotionGetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/api/notion")
@RequiredArgsConstructor
public class NotionAPIController {
    private final NotionManageUsecaseImpl notionManageUsecaseImpl;

    @GetMapping("/page/{pageId}")
    @Operation(summary = "페이지 가져오기")
    public ResponseEntity<Map<String, Object>> getPage(@PathVariable String pageId) {
        Map<String, Object> pageData = notionManageUsecaseImpl.getPage(pageId);
        return ResponseEntity.ok(pageData);
    }
}
