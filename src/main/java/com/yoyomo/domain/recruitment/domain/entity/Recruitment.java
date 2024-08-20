package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
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

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "recruitment")
    private List<Process> processes;

    @PrePersist
    public void init() {
        processes = new ArrayList<>();  // 수정: 추후 필드에 init
        isActive = true;
        totalApplicantsCount = 0;
    }

    public void addProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void addProcess(Process process) {
        this.processes.add(process);
    }

    public void sortProcess() {
        this.processes.sort(Comparator.comparing(Process::getStage));
    }


    public void update(Update dto) {
        this.title = dto.title();
        this.position = dto.position();
        this.generation = dto.generation();
        this.isActive = dto.isActive();
        this.formId = dto.formId();
    }

    public void clearProcesses() {
        this.processes.clear();
    }

    public void delete() {
        isActive = false;
    }

    public static void checkEnabled(Recruitment recruitment) {
        if(!recruitment.isActive)
            throw new RecruitmentNotFoundException();
    }
}
