package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.form.domain.entity.Form;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static com.yoyomo.domain.recruitment.domain.entity.RecruitmentStatus.PRE_RECRUITMENT;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recruitments")
public class Recruitment {

    @Id
    private String id;

    private String clubId;

    private String title;

    private int generation;

    private String position;

    private Form form;

    private List<Process> processes;

    @Builder.Default
    private RecruitmentStatus recruitmentStatus = PRE_RECRUITMENT;

    @Builder.Default
    private int processStage = 0;

    @Builder.Default
    private Boolean isRecruitmentActive = false;

    private LocalDateTime deletedAt;

    public void remainOnlyAnnouncedProcess() {
        processes.removeIf(Process::isNotAnnounced);
    }
}
