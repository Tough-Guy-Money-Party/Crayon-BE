package com.yoyomo.domain.club.domain.entity;

import com.yoyomo.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    private String subDomain;

    @OneToMany(mappedBy = "club")
    private List<ClubManager> clubManagers;
}
