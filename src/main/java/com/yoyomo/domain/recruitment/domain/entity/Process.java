package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.mail.exception.MailNotScheduledException;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.exception.ProcessStepUnModifiableException;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.yoyomo.domain.recruitment.presentation.constant.ResponseMessage.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Process extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long id;

    private String title;

    private int stage;

    @Builder.Default
    @Column(nullable = false, name = "process_step")
    @Enumerated(EnumType.STRING)
    private ProcessStep processStep = ProcessStep.EVALUATION;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime announceStartAt;

    private LocalDateTime announceEndAt;

    private LocalDateTime mailScheduledAt;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    public void updateStep(ProcessStep step) {
        this.processStep = step;
    }

    public void reserve(LocalDateTime scheduledTime) {
        this.mailScheduledAt = scheduledTime;
    }

    public void cancelMail() {
        this.mailScheduledAt = null;
    }

    public void checkMailScheduled() {
        if (this.mailScheduledAt == null) {
           throw new MailNotScheduledException();
        }
    }

    public void checkMovable(Type currentProcess, ProcessStep step) {
        if (this.type != currentProcess) {
            throw new ProcessStepUnModifiableException(PROCESS_STEP_CANNOT_UPDATE);
        }

        if (step == ProcessStep.EVALUATION && this.isAfterMailSent()) {
            throw new ProcessStepUnModifiableException(CANNOT_UPDATE_TO_EVALUATION_STEP);
        }

        if (this.type == Type.INTERVIEW && step == ProcessStep.MOVING) {
            throw new ProcessStepUnModifiableException(CANNOT_UPDATE_TO_MOVING_STEP);
        }
    }

    private boolean isAfterMailSent() {
        if (this.mailScheduledAt != null) {
            return LocalDateTime.now().isAfter(this.getMailScheduledAt());
        }
        return false;
    }
}
