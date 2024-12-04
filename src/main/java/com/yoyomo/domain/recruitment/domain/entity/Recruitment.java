package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.application.exception.OutOfDeadlineException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Submit;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.domain.recruitment.exception.RecruitmentUnmodifiableException;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Update;
import static com.yoyomo.domain.recruitment.domain.entity.enums.Status.RECRUITING;
import static com.yoyomo.domain.recruitment.domain.entity.enums.Status.getStatus;

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

    private String generation;

    @Enumerated(EnumType.STRING)
    private Submit submit;

    private boolean isActive;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private String formId;

    private int totalApplicantsCount;   // 수정: applicant++

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Process> processes = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false, name = "process_step")
    @Enumerated(EnumType.STRING)
    private ProcessStep processStep = ProcessStep.EVALUATION;

    public void addProcesses(List<Process> processes) {
        this.processes.clear();
        this.processes.addAll(processes);
    }

    public void update(Update dto) {
        this.title = dto.title();
        this.position = dto.position();
        this.generation = dto.generation();
    }

    public void activate(String formId) {
        this.formId = formId;
        this.isActive = true;
    }

    public void checkModifiable() {
        if (this.isActive)
            throw new RecruitmentUnmodifiableException();
    }

    public void clearProcesses() {
        this.processes.clear();
    }

    public void close() {
        this.deletedAt = LocalDateTime.now();
        this.isActive = false;
    }

    public boolean isBefore() {
        return this.startAt.isAfter(LocalDateTime.now());
    }

    public boolean isAfter() {
        return this.endAt.isBefore(LocalDateTime.now());
    }

    public void plusApplicantsCount() {
        this.totalApplicantsCount++;
    }

    public void minusApplicantsCount() {
        this.totalApplicantsCount--;
    }

    public void checkAvailable() {
        if (this.deletedAt != null) {
            throw new RecruitmentNotFoundException();
        }

        if (getStatus(this) != RECRUITING) {
            throw new OutOfDeadlineException();
        }
    }

    public boolean isLastProcess(Process current) {
        Long lastProcessId = processes.get(processes.size() - 1).getId();
        return Objects.equals(lastProcessId, current.getId());
    }

    public LocalDate getEndDate() {
        return endAt.toLocalDate();
    }

    public Process getDocumentProcess() {
        return processes.get(0);
    }
}
