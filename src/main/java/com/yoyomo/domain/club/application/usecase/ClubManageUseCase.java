package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.res.ClubCreateResponse;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;

public interface ClubManageUseCase {
    public ClubResponse read(String id);

    public ClubCreateResponse create(ClubRequest request);

    public void update(String id, ClubRequest request);

    public void delete(String id);
}
