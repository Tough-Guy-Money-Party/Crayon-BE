package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.user.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
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
    private User user;

    @NotBlank
    private String recruitmentId;

    @NotNull
    private List<Answer> answers;

    @NotNull
    private SubmitStatus submitStatus;

    @NotNull
    @Builder.Default
    private ApplicationStatus applicationStatus = PENDING;
}
