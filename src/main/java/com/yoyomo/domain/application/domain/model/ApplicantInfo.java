package com.yoyomo.domain.application.domain.model;

import com.yoyomo.domain.application.exception.InvalidApplicantInfoException;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ApplicantInfo {
    NAME("이름"),
    PHONE("전화번호"),
    EMAIL("메일");

    private final String keyword;

    public static ApplicantInfo find(QuestionReply questionReply) {
        return Arrays.stream(values())
                .filter(info -> questionReply.match(info.keyword))
                .findFirst()
                .orElseThrow(InvalidApplicantInfoException::new);
    }

    public static boolean anyMatch(String title) {
        return Arrays.stream(values())
                .anyMatch(info -> title.contains(info.keyword));
    }
}
