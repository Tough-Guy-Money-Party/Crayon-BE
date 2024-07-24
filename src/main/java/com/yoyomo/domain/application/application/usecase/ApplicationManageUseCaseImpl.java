package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationStatusRequest;
import com.yoyomo.domain.application.application.dto.req.AssessmentRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationManageResponse;
import com.yoyomo.domain.application.application.dto.res.ApplicationResponse;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.AssessmentRating;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.exception.AlreadySubmitApplicationException;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.user.application.usecase.ManagerInfoUseCaseImpl;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.application.domain.entity.AssessmentRating.calculate;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCaseImpl implements ApplicationManageUseCase {
    private final ApplicationGetService applicationGetService;
    private final ApplicationUpdateService applicationUpdateService;
    private final ApplicationMapper applicationMapper;
    private final RecruitmentUpdateService recruitmentUpdateService;
    private final RecruitmentGetService recruitmentGetService;
    private final ManagerInfoUseCaseImpl managerInfoUseCaseImpl;

    @Override
    public void checkReadPermission(Applicant applicant, Application application) {
        if (!applicant.getId().equals(application.getApplicant().getId())) {
            throw new AccessDeniedException();
        }
    }

    @Override
    public void checkDuplicatedApplication(Applicant applicant, String recruitmentId) {
        boolean hasApplication = applicationGetService.exists(applicant, recruitmentId);
        if (hasApplication) {
            throw new AlreadySubmitApplicationException();
        }
    }

    @Override
    public Page<ApplicationResponse> readAll(String recruitmentId, Integer stage, Pageable pageable) {
        return applicationGetService.findAll(recruitmentId, stage, pageable)
                .map(application -> applicationMapper.mapToApplicationResponse(application, recruitmentGetService.find(application.getRecruitmentId())));
    }

    @Override
    public List<ApplicationResponse> readAllByApplicantName(String recruitmentId, String name, int pageNum) {
        List<Application> applications = applicationGetService.findAllByApplicantName(recruitmentId, name, pageNum);
        return applications.stream()
                .map(application -> applicationMapper.mapToApplicationResponse(application, recruitmentGetService.find(application.getRecruitmentId())))
                .toList();
    }

    @Override
    public ApplicationManageResponse read(String applicationId) {
        Application application = applicationGetService.find(applicationId);
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        return applicationMapper.mapToApplicationManage(application, recruitment);
    }

    @Override
    public void update(String id, ApplicationStatusRequest request) {
        applicationUpdateService.from(id, request);
    }

    @Override
    public void addAssessment(String id, AssessmentRequest request, Authentication authentication) {
        Manager manager = managerInfoUseCaseImpl.get(authentication);

        Application oldApplication = applicationGetService.find(id);
        AssessmentRating assessmentRating = calculate(oldApplication, request.assessmentRating());
        applicationUpdateService.from(id, request, manager, assessmentRating);

        Application newApplication = applicationGetService.find(id);

        Recruitment recruitment = recruitmentGetService.find(newApplication.getRecruitmentId());
        recruitment.getProcesses().stream()
                .filter(process -> process.getStage() == newApplication.getCurrentStage())
                .findAny()
                .ifPresent(process -> {
                    process.getApplications().replaceAll(application ->
                            application.equals(oldApplication) ? newApplication : application
                    );
                });

        recruitmentUpdateService.from(newApplication.getRecruitmentId(), recruitment.getProcesses());
    }

    @Override
    public void update(String id, Integer from, Integer to) {
        // [Application] currentStage update -> ApplicationUpdateService
        Application application = applicationGetService.find(id);
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());

        // [Process] applications update 1
        recruitment.getProcesses().stream()
                .filter(process -> process.getStage() == from)
                .findFirst()
                .ifPresent(process -> process.getApplications().removeIf(app -> app.getId().equals(id)));

        applicationUpdateService.from(id, to);
        application.updateStage(to);

        // [Process] applications update 2
        recruitment.getProcesses().stream()
                .filter(process -> process.getStage() == to)
                .findFirst()
                .ifPresent(process -> process.getApplications().add(application));

        // [Recruitment] processes update
        recruitmentUpdateService.from(recruitment.getId(), recruitment.getProcesses());
    }
}
