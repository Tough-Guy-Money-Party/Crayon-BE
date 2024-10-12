package com.yoyomo.infra.notion.service;

import com.yoyomo.infra.notion.exception.InvalidNotionLinkException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotionGetService {
    public String notionParser(String notionLink) {
        String patternString = "^https:\\/\\/(www\\.notion\\.so|[^\\/]+\\.notion\\.site)\\/[^\\?\\/]*([0-9a-fA-F]{32})";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(notionLink);

        if (matcher.find()) {
            return matcher.group(2);
        } else {
            throw new InvalidNotionLinkException();
        }
    }
}
