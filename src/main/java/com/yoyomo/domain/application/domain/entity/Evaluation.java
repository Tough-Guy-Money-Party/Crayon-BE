package com.yoyomo.domain.application.domain.entity;

import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(nullable = false, name = "process_id")
    private long processId;

    private String memo;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public boolean isAfterEvaluation() { // todo pending 값 말고 존재 유무로 가능
        return rating != Rating.PENDING;
    }

    public void update(Rating rating, String memo) {
        this.rating = rating;
        this.memo = memo;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
