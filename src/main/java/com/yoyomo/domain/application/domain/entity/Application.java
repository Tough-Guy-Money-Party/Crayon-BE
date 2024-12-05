package com.yoyomo.domain.application.domain.entity;


import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.exception.AccessDeniedException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String userName;

    private String email;

    @Column(length = 13)
    private String tel;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.BEFORE_EVALUATION;

    private String answerId;

    @Column(nullable = false, name = "recruitment_id")
    private UUID recruitmentId;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    @Embedded
    private Interview interview;

    private LocalDateTime deletedAt;

    public void update(Process process) {
        this.process = process;
        this.status = Status.PENDING;
    }

    public void addInterview(Interview interview) {
        this.interview = interview;
    }

    public void addAnswer(String answerId) {
        this.answerId = answerId;
    }

    public void evaluate(Status status) {
        this.status = status;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void checkAuthorization(User user) {
        if (!this.getUser().equals(user)) {
            throw new AccessDeniedException();
        }
    }

    public boolean isBeforeInterview(List<Type> types) {

        if (!types.contains(Type.INTERVIEW)) {
            return false;
        }

        return types.indexOf(Type.INTERVIEW) > this.getProcess().getStage();
    }
}
