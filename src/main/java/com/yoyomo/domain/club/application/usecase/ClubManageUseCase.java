package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.req.RemoveManagerRequest;
import com.yoyomo.domain.club.application.dto.res.ClubCreateResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.application.dto.res.ParticipationResponse;

public interface ClubManageUseCase {
    ClubResponse read(String id);

    ClubCreateResponse create(ClubRequest request, String userEmail);

    ParticipationResponse participate(ParticipationRequest participateRequest, String userEmail);

    void removeManager(RemoveManagerRequest removeManagerRequest);

    void update(String id, ClubRequest request);

    void delete(String id);
}
