package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Process extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long id;

    private String title;

    private Integer stage;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime announceStartAt;

    private LocalDateTime announceEndAt;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "process")
    private List<Application> applications = new ArrayList<>();

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public void update(ProcessRequestDTO.Update update) {
        this.title = update.title();
        this.stage = update.stage();
        this.type = update.type();
        this.startAt = update.startAt();
        this.endAt = update.endAt();
        this.announceStartAt = update.announceStartAt();
        this.announceEndAt = update.announceEndAt();
    }
}
