package com.yoyomo.domain.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

import static com.yoyomo.domain.application.domain.entity.ApplicationStatus.PENDING;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "applications")
public class Application {
    @Id
    private String id;

    private String recruitmentId;

    private Map<String, String> answers;

    private SubmitStatus submitStatus;

    @Builder.Default
    private ApplicationStatus applicationStatus = PENDING;
}
