package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.dto.res.ApplicationDetailsResponse;
import com.yoyomo.domain.application.application.dto.res.MyApplicationsResponse;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentUpdateService;
import com.yoyomo.domain.user.application.usecase.ApplicantInfoUseCaseImpl;
import com.yoyomo.domain.user.application.usecase.ApplicantManageUseCase;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyUseCaseImpl implements ApplyUseCase {
    private final ApplicationManageUseCase applicationManageUseCase;
    private final ApplicationSaveService applicationSaveService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationUpdateService applicationUpdateService;
    private final ApplicationMapper applicationMapper;
    private final RecruitmentGetService recruitmentGetService;
    private final ClubGetService clubGetService;
    private final ApplicantInfoUseCaseImpl ApplicantInfoUseCaseImpl;
    private final ApplicantManageUseCase applicantManageUseCase;
    private final RecruitmentUpdateService recruitmentUpdateService;

    @Override
    public void create(ApplicationRequest request) {
        Applicant applicant = getUserOrCreateNew(request);
        Recruitment recruitment = recruitmentGetService.find(request.recruitmentId());
        Application application = applicationMapper.from(applicant, recruitment.getId(), request);
        applicationSaveService.save(application);

        recruitment.getProcesses().get(0).addApplication(application);
        recruitmentUpdateService.from(recruitment.getId(), recruitment.getProcesses());
    }

    private Applicant getUserOrCreateNew(ApplicationRequest request) {
        try {
            Applicant applicant = ApplicantInfoUseCaseImpl.get(request.name(), request.phone());
            applicationManageUseCase.checkDuplicatedApplication(applicant, request.recruitmentId());
            return applicant;
        } catch (UserNotFoundException e) {
            return applicantManageUseCase.create(request);
        }
    }

    @Override
    public void update(Applicant applicant, String applicationId, ApplicationRequest request) {
        applicationManageUseCase.checkDuplicatedApplication(applicant, request.recruitmentId());
        Application application = applicationGetService.find(applicationId);
        applicationManageUseCase.checkReadPermission(applicant, application);
        applicationUpdateService.from(applicationId, request);
    }

    @Override
    public ApplicationDetailsResponse read(Applicant applicant, String applicationId) {
        Application application = applicationGetService.find(applicationId);
        applicationManageUseCase.checkReadPermission(applicant, application);
        return applicationMapper.mapToApplicationDetails(application);
    }

    @Override
    public List<MyApplicationsResponse> readAll(Applicant applicant, int pageNum) {
        List<Application> applications = applicationGetService.findAll(applicant, pageNum);
        return applications.stream()
                .map(application -> {
                    Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
                    Club club = clubGetService.byId(recruitment.getClubId());
                    return applicationMapper.mapToMyApplications(application, club);
                }).toList();
    }
}
