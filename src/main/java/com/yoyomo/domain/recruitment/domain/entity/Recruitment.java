package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Update;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Recruitment extends BaseEntity {

    @Id
    @Column(name = "recruitment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String position;

    private Integer generation;

    private Status status;

    private Boolean isActive;

    private String formId;

    private Integer totalApplicantsCount;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "recruitment")
    private List<Process> processes;

    @PrePersist
    public void init() {
        processes = new ArrayList<>();  // 수정: 추후 필드에 init
        isActive = false;
        totalApplicantsCount = 0;
    }

    public void addProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void update(Update dto) {
        this.title = dto.title();
        this.position = dto.position();
        this.generation = dto.generation();
        this.isActive = dto.isActive();
    }

    public void activate(String formId) {
        this.formId = formId;
        this.isActive = true;
    }

    public void clearProcesses() {
        this.processes.clear();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public static void checkEnabled(Recruitment recruitment) {
        if(recruitment.deletedAt != null)
            throw new RecruitmentNotFoundException();
    }
}
