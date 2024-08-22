package com.yoyomo.domain.application.domain.entity;


import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.interview.domain.entity.Interview;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    private Status status;

    private Rating averageRating;

    private String answerId;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    @OneToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @OneToMany(mappedBy = "application")
    private List<Evaluation> evaluations;
}
