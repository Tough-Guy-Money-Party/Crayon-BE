package com.yoyomo.domain.club.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubLandingStyle {
    private String callToAction;
    private String buttonColor;
    private String textColor;
    private DisplayMode displayMode;
}
