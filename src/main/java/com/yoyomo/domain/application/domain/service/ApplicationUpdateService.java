package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.repository.ProcessResultRepository;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUpdateService {

    private final ApplicationRepository applicationRepository;
    private final ProcessResultRepository processResultRepository;
    private final RecruitmentRepository recruitmentRepository;

    public void delete(Application application) {
        recruitmentRepository.decreaseApplicantCount(application.getRecruitmentId());
        application.delete();
    }

    public void update(Application application, Interview interview) {
        application.addInterview(interview);
    }

    public void evaluate(Application application, Status status) {
        Optional<ProcessResult> optionalProcessResult = processResultRepository.findByApplicationIdAndProcessId(application.getId(), application.getProcess().getId());

        optionalProcessResult.ifPresentOrElse(processResult -> processResult.updateStatus(status),
                () -> {
                    ProcessResult processResult = ProcessResult.builder()
                            .applicationId(application.getId())
                            .processId(application.getProcess().getId())
                            .status(status)
                            .build();

                    processResultRepository.save(processResult);
                });
    }

    public void updatePassApplicants(List<UUID> applications, Process process) {
        applicationRepository.updateProcess(applications, process, Status.BEFORE_EVALUATION);
    }
}
