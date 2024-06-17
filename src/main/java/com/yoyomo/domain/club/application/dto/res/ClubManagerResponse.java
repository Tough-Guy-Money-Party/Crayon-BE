package com.yoyomo.domain.club.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubManagerResponse {
    private String id;
    private String name;
    private String email;
}

