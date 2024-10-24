package com.yoyomo.domain.landing.domain.entity;


import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Landing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "LandingId")
    private UUID id;
    private String favicon;
    private String image;
    private String siteTitle;
    private String callToAction;
    private String buttonColor;
    private String textColor;
    @OneToOne(mappedBy = "landing")
    private Club club;

    public Landing(Club club) {
        this.club = club;
    }

    public void updateStyle(Style dto) {
        this.callToAction = dto.callToAction();
        this.buttonColor = dto.buttonColor();
        this.textColor = dto.textColor();
    }
}
