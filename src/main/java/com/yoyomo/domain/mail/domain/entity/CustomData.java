package com.yoyomo.domain.mail.domain.entity;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.global.common.util.DateFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomData {

    private final String email;
    private final Map<CustomType, String> data;
    private final boolean isPass;

    public static CustomData of(Application application, Status status) {
        Interview interview = application.getInterview();
        Map<CustomType, String> data = Map.of(
                CustomType.USER_NAME, application.getUserName(),
                CustomType.INTERVIEW_DATE, getInterviewDate(interview),
                CustomType.INTERVIEW_PLACE, getInterviewPlace(interview)
        );
        return new CustomData(application.getEmail(), data, status.isPass());
    }

    private static String getInterviewDate(Interview interview) {
        if (interview == null) {
            return "";
        }
        String date = interview.getDate();
        return DateFormatter.formatMailDate(date);
    }

    private static String getInterviewPlace(Interview interview) {
        if (interview == null) {
            return "";
        }
        return interview.getPlace();
    }
}
