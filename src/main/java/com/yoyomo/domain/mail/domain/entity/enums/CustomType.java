package com.yoyomo.domain.mail.domain.entity.enums;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum CustomType {
    CLUB_NAME("CLUB_NAME") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> recruitment.getClub().getName(), "");
        }
    },
    USER_NAME("USER_NAME") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> application.getUser().getName(), "");
        }
    },
    RECRUITMENT_NAME("RECRUITMENT_NAME") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(recruitment::getTitle, "");

        }
    },
    INTERVIEW_DATE("INTERVIEW_DATE") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> application.getInterview().getDate(), "");

        }
    },
    INTERVIEW_PLACE("INTERVIEW_PLACE") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> application.getInterview().getPlace(), "");
        }
    },
    PROCESS("PROCESS") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> application.getProcess().getTitle(), "");
        }
    };

    private final String placeholder;

    public abstract String extractValue(Application application, Recruitment recruitment);

    private static <T> T get(Supplier<T> supplier, T defaultValue) {
        try {
            return Optional.ofNullable(supplier.get()).orElse(defaultValue);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }
}
