package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.UpdateStyleSettingsRequest;
import com.yoyomo.domain.club.application.dto.res.ClubGeneralSettingResponse;
import com.yoyomo.domain.club.application.dto.res.ClubStyleSettingsResponse;

public interface LandingManageUseCase {

    void update(UpdateStyleSettingsRequest request, String email);

    ClubGeneralSettingResponse getGeneralSetting(String email);

    ClubStyleSettingsResponse getStyleSetting(String email);

    void create(String email, String notionPageLink);
}
