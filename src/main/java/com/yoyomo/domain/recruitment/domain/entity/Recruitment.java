package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Recruitment {

    @Id
    @Column(name = "recruitment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String position;

    private Integer generation;

    private Status status;

    private Boolean isActive;

    private String formId;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "recruitment")
    private List<Process> processes;
}
