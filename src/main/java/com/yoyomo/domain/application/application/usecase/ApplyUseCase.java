package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.user.domain.entity.User;

import java.util.List;

public interface ApplyUseCase {
    void create(ApplicationRequest applicationRequest);

    void update(User user, String applicationId, ApplicationRequest applicationRequest);

    ApplicationDetailsResponse read(User user, String applicationId);

    List<MyApplicationsResponse> readAll(User user);
}
