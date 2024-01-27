package com.yoyomo.domain.application.domain.entity;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String userId;

    @NotBlank
    private String recruitmentId;

    @NotBlank
    private List<Answer> answers;

    @NotBlank
    private SubmitStatus submitStatus;

    @NotBlank
    @Builder.Default
    private ApplicationStatus applicationStatus = PENDING;
}
