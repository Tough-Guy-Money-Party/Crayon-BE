package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.interview.domain.entity.Interview;
import com.yoyomo.domain.user.domain.entity.Applicant;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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
    private String recruitmentId;

    @NotNull
    private List<Answer> answers;

    @NotNull
    private SubmitStatus submitStatus;

    private AssessmentStatus assessmentStatus;

    @NotNull
    @Builder.Default
    private int currentStage = 0;

    @NotNull
    private List<Assessment> assessments;

    private Interview interview;

    @NotNull
    private LocalDateTime createdAt;

    public boolean hasInterview() {
        return interview != null;
    }

    public void updateStage(Integer stage) {
        this.currentStage = stage;
    }
}
