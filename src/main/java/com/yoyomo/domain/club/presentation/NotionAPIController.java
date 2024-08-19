package com.yoyomo.domain.club.presentation;

import com.yoyomo.domain.club.domain.service.NotionAPI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notion")
public class NotionAPIController {

    private final NotionAPI notionAPI;

    @GetMapping("/page/{pageId}")
    public ResponseEntity<Map<String, Object>> getPage(@PathVariable String pageId) {
        Map<String, Object> pageData = notionAPI.getPage(pageId);
        return ResponseEntity.ok(pageData);
    }

}

