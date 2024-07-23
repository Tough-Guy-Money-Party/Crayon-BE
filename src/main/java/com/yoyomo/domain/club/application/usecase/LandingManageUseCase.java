package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.ClubGeneralSettingResponse;
import org.springframework.security.core.Authentication;

public interface LandingManageUseCase {

    void update(UpdateStyleSettingsRequest request, String userEmail);

    ClubGeneralSettingResponse getGeneralSetting(Authentication authentication);

    void create(Authentication authentication, String notionPageLink);
}
