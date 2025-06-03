package com.yoyomo.domain.application.domain.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Question {

    private final String title;
    private final String type;

    public boolean isDateType() {
        return "datetime".equals(type);
    }

    public boolean isApplicantInfo() {
        return ApplicantInfo.anyMatch(title);
    }

    public boolean match(String keyword) {
        return this.title.contains(keyword);
    }

    public String getTitle() {
        return title;
    }
}
