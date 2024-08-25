package com.yoyomo.domain.user.domain.entity;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.club.domain.entity.ClubManager;
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
public class Manager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    private String name;

    private String email;

    private String refreshToken;

    private LocalDateTime deletedAt;

    @Builder.Default
    @OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ClubManager> clubManagers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addClubManager(ClubManager clubManager) {
        this.clubManagers.add(clubManager);
    }

    public void addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
    }
}
