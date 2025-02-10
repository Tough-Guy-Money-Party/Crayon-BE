package com.yoyomo.domain.mail.domain.entity.enums;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.global.common.util.DateFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum CustomType {
    CLUB_NAME("clubName") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> recruitment.getClub().getName(), "");
        }
    },
    USER_NAME("userName") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(application::getUserName, "");
        }
    },
    RECRUITMENT_NAME("recruitmentName") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(recruitment::getTitle, "");

        }
    },
    INTERVIEW_DATE("interviewDate") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            String date = application.getInterview().getDate();
            String formattedDate = DateFormatter.formatMailDate(date);
            return get(() -> formattedDate, "");
        }
    },
    INTERVIEW_PLACE("interviewPlace") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> application.getInterview().getPlace(), "");
        }
    },
    PROCESS("process") {
        @Override
        public String extractValue(Application application, Recruitment recruitment) {
            return get(() -> application.getProcess().getTitle(), "");
        }
    };

    private final String placeholder;

    public abstract String extractValue(Application application, Recruitment recruitment);

    private static <T> T get(Supplier<T> supplier, T defaultValue) {
        try {
            return java.util.Optional.ofNullable(supplier.get()).orElse(defaultValue);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    public static Optional<CustomType> findCustomType(String placeholder) {
        for (CustomType customType : CustomType.values()) {
            if (customType.getPlaceholder().equalsIgnoreCase(placeholder)) {
                return Optional.of(customType);
            }
        }
        return Optional.empty();
    }
}
