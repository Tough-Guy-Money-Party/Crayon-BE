package com.yoyomo.domain.club.domain.entity;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@ToString
public class Club extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "club_id")
    private UUID id;

    private String name;

    private String subDomain;

    @OneToMany(mappedBy = "club")
    private List<ClubManager> clubManagers;

    private LocalDateTime deletedAt;

    @PrePersist
    public void init() {
        clubManagers = new ArrayList<>();
    }

    public void addClubManager(ClubManager clubManager) {
        this.clubManagers.add(clubManager);
    }

    public void update(ClubRequestDTO.Save dto) {
        this.name = dto.name();
        this.subDomain = dto.subDomain();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
