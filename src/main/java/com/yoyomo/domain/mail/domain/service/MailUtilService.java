package com.yoyomo.domain.mail.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MailUtilService {

    public Map<String, String> createCustomData(Application application, Recruitment recruitment, Set<CustomType> customTypes) {
        Map<String, String> dataMap = new HashMap<>();

        for (CustomType customType : customTypes) {
            String value = customType.extractValue(application, recruitment);
            dataMap.put(customType.getPlaceholder(), value);
        }
        return dataMap;
    }

    public Set<CustomType> extract(MailTemplateSaveRequest dto) {
        String htmlPart = dto.htmlPart();

        Document doc = Jsoup.parse(htmlPart);

        return doc.select("[data-id]").stream()
                .map(element -> element.attr("data-id"))
                .map(this::findCustomType)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    private Optional<CustomType> findCustomType(String placeholder) {
        for (CustomType customType : CustomType.values()) {
            if (customType.getPlaceholder().equals(placeholder)) {
                return Optional.of(customType);
            }
        }
        return Optional.empty();
    }
}
