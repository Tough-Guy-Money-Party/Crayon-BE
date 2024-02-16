package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.req.ParticipationRequest;
import com.yoyomo.domain.club.application.dto.res.ClubCreateResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;

public interface ClubManageUseCase {
    ClubResponse read(String id);

    ClubCreateResponse create(ClubRequest request, String userEmail);
    void participate(ParticipationRequest participateRequest, String userEmail);

    void update(String id, ClubRequest request);

    void delete(String id);
}
