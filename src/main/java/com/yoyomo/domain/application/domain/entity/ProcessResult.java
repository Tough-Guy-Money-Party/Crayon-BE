package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.application.domain.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"application_id", "process_id"}
        )})
public class ProcessResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_result_id")
    private Long id;

    @Column(name = "application_id", nullable = false)
    private UUID applicationId;

    @Column(name = "process_id", nullable = false)
    private long processId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.BEFORE_EVALUATION;

    public static ProcessResult getBeforeEvaluationResult(UUID applicationId, long processId) {
        return ProcessResult.builder()
                .applicationId(applicationId)
                .processId(processId)
                .build();
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
