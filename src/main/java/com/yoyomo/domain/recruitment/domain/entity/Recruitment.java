package com.yoyomo.domain.recruitment.domain.entity;

import static com.yoyomo.domain.recruitment.domain.entity.enums.Status.RECRUITING;
import static com.yoyomo.domain.recruitment.domain.entity.enums.Status.getStatus;

import com.yoyomo.domain.application.exception.OutOfDeadlineException;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.enums.Submit;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.domain.recruitment.exception.RecruitmentUnmodifiableException;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type currentProcess = Type.FORM;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Process> processes = new ArrayList<>();

    public static Recruitment replicate(Recruitment recruitment) {
        return Recruitment.builder()
                .generation(recruitment.generation)
                .title(recruitment.title)
                .submit(recruitment.submit)
                .isActive(false)
                .startAt(recruitment.startAt)
                .endAt(recruitment.endAt)
                .currentProcess(Type.FORM)
                .club(recruitment.club)
                .processes(new ArrayList<>())
                .build();
    }

    public void addProcesses(List<Process> processes) {
        this.processes.clear();
        this.processes.addAll(processes);
    }

    public void addNewProcesses(List<Process> processes) {
        this.processes.addAll(processes);
    }

    public void activate(String formId) {
        this.formId = formId;
        this.isActive = true;
    }

    public void checkModifiable() {
        if (this.isActive) {
            throw new RecruitmentUnmodifiableException();
        }
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

    public LocalDate getEndDate() {
        return endAt.toLocalDate();
    }

    public Process getDocumentProcess() {
        return processes.get(0);
    }

    public void updateProcess(Type type) {
        this.currentProcess = type;
    }

    public void update(String title, String position, LocalDateTime startAt, LocalDateTime endAt) {
        this.title = title;
        this.position = position;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}

