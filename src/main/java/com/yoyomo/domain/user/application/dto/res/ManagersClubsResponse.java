package com.yoyomo.domain.user.application.dto.res;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagersClubsResponse {
    private List<Club> clubs;
}