package com.yoyomo.domain.club.domain.entity;

import com.yoyomo.domain.user.domain.entity.Manager;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ClubManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_manager_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public ClubManager(Manager manager, Club club) {
        this.manager = manager;
        this.club = club;
    }
}
