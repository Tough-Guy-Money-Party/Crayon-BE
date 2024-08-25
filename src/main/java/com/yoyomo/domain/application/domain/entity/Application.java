package com.yoyomo.domain.application.domain.entity;


import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.yoyomo.domain.application.domain.entity.enums.Rating.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_id")
    private UUID id;

    @Embedded
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Rating averageRating;

    private String answerId;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    @Embedded
    private Interview interview;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "application")
    private List<Evaluation> evaluations = new ArrayList<>();

    @PrePersist
    public void init() {
        this.status = Status.PENDING;
        this.averageRating = PENDING;
    }

    public void mapToAnswer(String answerId) {
        this.answerId = answerId;
    }

    public boolean containsInRecruitment(Recruitment recruitment) {
        return this.process.getRecruitment().equals(recruitment);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void update(Process process) {
        this.process = process;
    }

    public void addInterview(Interview interview) {
        this.interview = interview;
    }

    public void evaluate(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        this.averageRating = calculate(this);
        this.status = evaluation.getStatus();
    }


}
