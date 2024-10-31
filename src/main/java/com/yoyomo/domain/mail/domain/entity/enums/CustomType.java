package com.yoyomo.domain.mail.domain.entity.enums;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum CustomType {
    CLUB_NAME("clubName") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return Optional.ofNullable(recruitment.getClub().getName()).orElse("");
        }
    },
    USER_NAME("userName") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return Optional.ofNullable(application.getUser().getName()).orElse("");
        }
    },
    RECRUITMENT_NAME("recruitmentName") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return Optional.ofNullable(recruitment.getTitle()).orElse("");
        }
    },
    INTERVIEW_DATE("interviewDate") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return Optional.ofNullable(application.getInterview().getDate()).orElse("");
        }
    },
    INTERVIEW_PLACE("interviewPlace") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return Optional.ofNullable(application.getInterview().getPlace()).orElse("");
        }
    },
    PROCESS("currentProcess") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return Optional.ofNullable(application.getProcess().getTitle()).orElse("");
        }
    };

    // 커스텀 항목이 확장된다면 여기에 인자를 하나 더 전달해서 데이터를 가져오면 됨. 속도는 테스트 해봐야하겟지만 큰 차이는 없을 듯
    private final String placeholder;

    // 각 CustomType에 대한 추상 메서드. 예외를 날릴지 null로 채울지 고민
    public abstract String extractValue(Application application, Recruitment recruitment);
}