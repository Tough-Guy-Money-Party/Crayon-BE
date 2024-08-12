package com.yoyomo.domain.club.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class NotionAPI {

    private final RestTemplate restTemplate;

    public Map<String, Object> getPage(String pageId) {
        String endpoint = "loadPageChunk";
        Map<String, Object> body = new HashMap<>();

        // Parse the pageId using your custom logic
        String parsedPageId = parsePageId(pageId);
        body.put("pageId", parsedPageId);

        body.put("limit", 100);
        body.put("chunkNumber", 0);

        Map<String, Object> cursor = new HashMap<>();
        cursor.put("stack", new ArrayList<>());
        body.put("cursor", cursor);

        body.put("verticalColumns", false);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://www.notion.so/api/v3")
                .pathSegment(endpoint);

        return this.fetch(uriBuilder.toUriString(), body);
    }

    private Map<String, Object> fetch(String url, Map<String, Object> body) {
        return restTemplate.postForObject(url, body, Map.class);
    }


    public String parsePageId(String pageId) {
        // Example logic to normalize a Notion page ID in Java
        String cleanedId = pageId.replace("-", ""); // Remove hyphens
        if (cleanedId.length() == 32) {
            // Format as a UUID (8-4-4-4-12)
            return String.format("%s-%s-%s-%s-%s",
                    cleanedId.substring(0, 8),
                    cleanedId.substring(8, 12),
                    cleanedId.substring(12, 16),
                    cleanedId.substring(16, 20),
                    cleanedId.substring(20));
        }
        return pageId; // Return as is if already formatted
    }

}


