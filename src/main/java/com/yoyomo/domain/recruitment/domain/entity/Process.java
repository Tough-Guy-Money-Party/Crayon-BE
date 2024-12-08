package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime mailScheduleAt;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    public void updateStep(ProcessStep step) {
        this.processStep = step;
    }

    public void reserve(LocalDateTime scheduledTime) {
        this.mailScheduleAt = scheduledTime;
    }
}
