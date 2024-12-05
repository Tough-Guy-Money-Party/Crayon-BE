package com.yoyomo.domain.template.domain.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class HtmlSanitizeService {

    public static String sanitize(String html) {
        if (html == null || html.isEmpty()) {
            return html;
        }

        Safelist customSafelist = Safelist.relaxed()
                .addTags("span", "ul", "li")
                .addAttributes(":all", "style", "class", "contenteditable", "data-id")
                .addAttributes("span", "data-id", "style")
                .addAttributes("ul", "style")
                .addAttributes("li", "style")
                .addAttributes("h1", "style")
                .addAttributes("p", "style")
                .addProtocols("a", "href", "http", "https")
                .addProtocols("img", "src", "http", "https");

        return Jsoup.clean(html, customSafelist);
    }
}
