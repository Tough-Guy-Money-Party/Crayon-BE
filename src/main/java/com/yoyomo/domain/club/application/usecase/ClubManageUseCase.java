package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.req.RemoveManagerRequest;
import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClubManageUseCase {
    ClubResponse read(String id);

    ClubCreateResponse create(ClubRequest request, String userEmail);

    ParticipationResponse participate(ParticipationRequest participateRequest, String userEmail);

    List<ClubManagerResponse> getManagers(Authentication authentication);

    String checkDuplicate(String subDomain);

    void removeManager(RemoveManagerRequest removeManagerRequest);

    void update(String id, ClubRequest request);

    void delete(String id);

}
