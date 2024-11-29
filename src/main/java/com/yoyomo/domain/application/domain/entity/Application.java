package com.yoyomo.domain.application.domain.entity;


import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

    @Embedded
    private User user;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Rating averageRating = Rating.PENDING;

    private String answerId;

    @Column(nullable = false, name = "recruitment_id")
    private UUID recruitmentId;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    @Embedded
    private Interview interview;

    private LocalDateTime deletedAt;

    public boolean inRecruitment(UUID recruitmentId) {
        return this.recruitmentId == recruitmentId;
    }

    public void update(Process process) {
        this.process = process;
        this.status = Status.PENDING;
        this.averageRating = Rating.PENDING;
    }

    public void addInterview(Interview interview) {
        this.interview = interview;
    }

    public void evaluate(Status status, Rating rating) {
        this.averageRating = rating;
        this.status = status;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
