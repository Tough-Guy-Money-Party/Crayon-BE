package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.user.domain.entity.User;

public interface ApplyUseCase {
    void create(User user, ApplicationRequest applicationRequest);

    ApplicationDetailsResponse read(User user, String applicationId);
}
