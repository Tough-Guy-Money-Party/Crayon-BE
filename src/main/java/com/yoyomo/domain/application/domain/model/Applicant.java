package com.yoyomo.domain.application.domain.model;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class Applicant {

    private final Map<ApplicantInfo, Reply> values;

    public String getName() {
        return values.getOrDefault(ApplicantInfo.NAME, Reply.empty()).value();
    }

    public String getPhone() {
        return values.getOrDefault(ApplicantInfo.PHONE, Reply.empty()).value();
    }

    public String getEmail() {
        return values.getOrDefault(ApplicantInfo.EMAIL, Reply.empty()).value();
    }
}
