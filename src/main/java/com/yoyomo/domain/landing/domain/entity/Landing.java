package com.yoyomo.domain.landing.domain.entity;


import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.General;
import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.domain.constant.DisplayMode;
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
    private String siteName;
    private String callToAction;
    private String buttonColor;
    private String textColor;
    private DisplayMode displayMode = DisplayMode.LIGHT;

    @OneToOne(mappedBy = "landing")
    private Club club;

    public Landing(Club club) {
        this.club = club;
    }

    public void updateStyle(Style dto) {
        this.callToAction = dto.callToAction();
        this.buttonColor = dto.buttonColor();
        this.textColor = dto.textColor();
        this.displayMode = dto.displayMode();
    }

    public void updateGeneral(General dto) {
        this.favicon = dto.favicon();
        this.image = dto.image();
        this.siteName = dto.siteName();
    }
}
