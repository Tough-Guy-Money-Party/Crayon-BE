package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.req.RemoveManagerRequest;
import com.yoyomo.domain.club.application.dto.res.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClubManageUseCase {
    ClubResponse read(String id);

    ClubCreateResponse create(ClubRequest request, String userEmail);

    ParticipationResponse participate(ParticipationRequest participateRequest, String userEmail);

    List<ClubManagerResponse> getManagers(Authentication authentication);

    ClubGeneralSettingResponse getGeneralSetting(Authentication authentication);

    void removeManager(RemoveManagerRequest removeManagerRequest);

    void update(String id, ClubRequest request);

    void delete(String id);

    void create(Authentication authentication, String notionPageLink);
}
