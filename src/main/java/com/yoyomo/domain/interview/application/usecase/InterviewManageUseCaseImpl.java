package com.yoyomo.domain.interview.application.usecase;

import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.interview.application.dto.InterviewRequest;
import com.yoyomo.domain.interview.application.mapper.InterviewMapper;
import com.yoyomo.domain.interview.domain.entity.Interview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewManageUseCaseImpl implements InterviewManageUseCase {
    private final ApplicationUpdateService applicationUpdateService;
    private final InterviewMapper interviewMapper;

    @Override
    public void create(String applicationId, InterviewRequest request) {
        Interview interview = interviewMapper.from(request);
        applicationUpdateService.from(applicationId, interview);
    }
}
