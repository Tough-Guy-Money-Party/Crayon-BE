package com.yoyomo.domain.template.application.support;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizer {

    public String sanitize(String html) {
        if (html == null || html.isEmpty()) {
            return html;
        }

        Safelist customSafelist = Safelist.relaxed()
                // 기본적으로 허용하는 태그들 외에 추가로 허용할 태그들
                .addTags("span", "ul", "li",
                        "recruitment-name", "club-name", "applicant",
                        "interview-date", "interview-place", "process")
                // 모든 태그에 공통으로 허용할 속성
                .addAttributes(":all", "style", "class", "contenteditable", "data-id")
                // 특정 태그에 허용할 추가 속성
                .addAttributes("span", "data-id", "style")
                .addAttributes("ul", "style")
                .addAttributes("li", "style")
                .addAttributes("h1", "style")
                .addAttributes("p", "style")
                .addAttributes("recruitment-name", "data-id", "style")
                .addAttributes("club-name", "data-id", "style")
                .addAttributes("applicant", "data-id", "style")
                .addAttributes("interview-date", "data-id", "style")
                .addAttributes("interview-place", "data-id", "style")
                .addAttributes("process", "data-id", "style")
                // 허용할 프로토콜
                .addProtocols("a", "href", "http", "https")
                .addProtocols("img", "src", "http", "https");

        return Jsoup.clean(html, customSafelist);
    }
}

