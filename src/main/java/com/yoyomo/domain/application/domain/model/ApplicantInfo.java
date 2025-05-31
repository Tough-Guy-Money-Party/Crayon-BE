package com.yoyomo.domain.application.domain.model;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ApplicantInfo {
    NAME("이름"),
    PHONE("전화번호"),
    EMAIL("메일");

    private final String keyword;

    public static ApplicantInfo find(Question question) {
        return Arrays.stream(values())
                .filter(info -> question.match(info.keyword))
                .findFirst()
                .orElseThrow(); // todo: 예외처리
    }

    public static boolean anyMatch(String title) {
        return Arrays.stream(values())
                .anyMatch(info -> title.contains(info.keyword));
    }
}
