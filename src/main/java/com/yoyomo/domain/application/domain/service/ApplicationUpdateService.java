package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUpdateService {

    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationRepository applicationRepository;

    public void delete(Application application) {
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        recruitment.minusApplicantsCount();
        application.delete();
    }

    public void update(Application application, Interview interview) {
        application.addInterview(interview);
    }

    public void evaluate(Application application, Status status) {
        application.evaluate(status);
    }

    public void updateProcess(List<Application> applications, Process to) {
        applicationRepository.updateProcess(to, Status.BEFORE_EVALUATION, applications);
    }
}
