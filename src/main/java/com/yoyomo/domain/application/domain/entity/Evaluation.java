package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Evaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    private Status status;

    private String memo;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public static boolean isAfterEvaluation(Evaluation evaluation) {
        return evaluation.getRating() != Rating.PENDING;
    }
}
