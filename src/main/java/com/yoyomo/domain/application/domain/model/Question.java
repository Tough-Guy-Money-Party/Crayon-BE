package com.yoyomo.domain.application.domain.model;

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

    public boolean match(String title) {
        return this.title.contains(title);
    }

    public String getTitle() {
        return title;
    }
}
