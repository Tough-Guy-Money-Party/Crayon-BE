package com.yoyomo.domain.process.domain.entity;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.process.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Process extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private Integer stage;

    private Type type;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime announceStartAt;

    private LocalDateTime announceEndAt;

    @ManyToOne
    private Recruitment recruitment;

    @OneToMany(mappedBy = "process")
    private List<Application> applications;
}
