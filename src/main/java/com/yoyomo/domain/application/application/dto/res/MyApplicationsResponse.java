package com.yoyomo.domain.application.application.dto.res;

import com.yoyomo.domain.application.domain.entity.ApplicationStatus;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.recruitment.domain.entity.Process;

import java.util.List;

public record MyApplicationsResponse(

        String id,
        ClubResponse club,
        List<Process> calendar,
        ApplicationStatus applicationStatus
) {
}
