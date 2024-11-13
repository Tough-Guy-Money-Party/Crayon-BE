package com.yoyomo.domain.club.domain.entity;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Update;
import com.yoyomo.domain.club.exception.ClubAccessDeniedException;
import com.yoyomo.domain.club.exception.DuplicatedParticipationException;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Club extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "club_id")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String subDomain;

    private String code;

    @Builder.Default
    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ClubManager> clubManagers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "landing_id")
    private Landing landing;

    private String notionPageLink;

    private LocalDateTime deletedAt;

    @PrePersist
    public void init() {
        generateCode();
    }

    public String generateCode() {
        code = UUID.randomUUID().toString();
        return code;
    }

    public void addClubManager(ClubManager clubManager) {
        this.clubManagers.add(clubManager);
    }

    public void addLanding(Landing landing) {
        this.landing = landing;
    }

    public void update(Update dto) {
        this.name = dto.name();
    }

    public void update(General dto) {
        this.subDomain = dto.subDomain();
        this.notionPageLink = dto.notionPageLink();
    }

    public void update(String notionPageLink) {
        this.notionPageLink = notionPageLink;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void checkDuplicateParticipate(Manager manager) {
        if (contains(manager)) {
            throw new DuplicatedParticipationException();
        }
    }

    public void checkAuthority(Manager manager) {
        if (!contains(manager)) {
            throw new ClubAccessDeniedException();
        }
    }

    private boolean contains(Manager manager) {
        return clubManagers.stream()
                .anyMatch(clubManager -> clubManager.getManager().equals(manager));
    }
}
