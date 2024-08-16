package com.yoyomo.domain.user.domain.entity;

import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "manager")
    private List<ClubManager> clubManagers;

    @PrePersist
    public void init() {
        clubManagers = new ArrayList<>();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addClubManager(ClubManager clubManager) {
        this.clubManagers.add(clubManager);
    }
}
