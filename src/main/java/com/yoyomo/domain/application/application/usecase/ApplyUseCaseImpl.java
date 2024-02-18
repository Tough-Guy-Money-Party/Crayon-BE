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
    private final com.yoyomo.domain.user.application.usecase.ApplicantInfoUseCase ApplicantInfoUseCase;
    private final ApplicantManageUseCase applicantManageUseCase;

    @Override
    public void create(ApplicationRequest request) {
        Applicant applicant = getUserOrCreateNew(request);
        Recruitment recruitment = recruitmentGetService.find(request.recruitmentId());
        Application application = applicationMapper.from(applicant, recruitment, request);
        applicationSaveService.save(application);
    }

    private Applicant getUserOrCreateNew(ApplicationRequest request) {
        try {
            Applicant applicant = ApplicantInfoUseCase.get(request.name(), request.phone());
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
    public List<MyApplicationsResponse> readAll(Applicant applicant) {
        List<Application> applications = applicationGetService.findAll(applicant);
        return applications.stream()
                .map(application -> {
                    Club club = clubGetService.byId(application.getRecruitment().getClubId());
                    return applicationMapper.mapToMyApplications(application, club);
                }).toList();
    }
}
