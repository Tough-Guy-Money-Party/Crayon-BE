package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.interview.domain.entity.Interview;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.Applicant;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.yoyomo.domain.application.domain.entity.ApplicationStatus.PENDING;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "applications")
public class Application {

    @Id
    private String id;

    @NotNull
    private Applicant applicant;

    @NotNull
    private Recruitment recruitment;

    @NotNull
    private List<Answer> answers;

    @NotNull
    private SubmitStatus submitStatus;

    @NotNull
    @Builder.Default
    private ApplicationStatus applicationStatus = PENDING;

    @NotNull
    private List<Assessment> assessments;

    private Interview interview;
}
