package com.yoyomo.infra.notion.service;

import com.yoyomo.infra.notion.exception.InvalidNotionLinkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NotionGetService {
    private final RestTemplate restTemplate;

    public String notionParser(String notionLink) {
        String patternString = "^https:\\/\\/(www\\.notion\\.so|[^\\/]+\\.notion\\.site)\\/[^\\?\\/]*([0-9a-fA-F]{32})";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(notionLink);

        if (matcher.find()){
            return matcher.group(2);
        } else {
            throw new InvalidNotionLinkException();
        }
    }


    public Map<String, Object> getPageChunk(String pageId) {
        String endpoint = "loadPageChunk";
        Map<String, Object> body = new HashMap<>();

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

    private String parsePageId(String pageId) {
        String cleanedId = pageId.replace("-", "");
        if (cleanedId.length() == 32) {
            return String.format("%s-%s-%s-%s-%s",
                    cleanedId.substring(0, 8),
                    cleanedId.substring(8, 12),
                    cleanedId.substring(12, 16),
                    cleanedId.substring(16, 20),
                    cleanedId.substring(20));
        }
        return pageId;
    }
}
