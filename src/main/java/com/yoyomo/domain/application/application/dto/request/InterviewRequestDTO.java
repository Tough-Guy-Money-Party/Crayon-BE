package com.yoyomo.domain.application.application.dto.request;

public class InterviewRequestDTO {

    public record Save(
            String place,
            String date
    ) {}
}
