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
import com.yoyomo.domain.user.application.usecase.UserInfoUseCase;
import com.yoyomo.domain.user.application.usecase.UserManageUseCase;
import com.yoyomo.domain.user.domain.entity.User;
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
    private final UserInfoUseCase userInfoUseCase;
    private final UserManageUseCase userManageUseCase;

    @Override
    public void create(ApplicationRequest request) {
        User user = getUserOrCreateNew(request);
        Recruitment recruitment = recruitmentGetService.find(request.recruitmentId());
        Application application = applicationMapper.from(user, recruitment, request);
        applicationSaveService.save(application);
    }

    private User getUserOrCreateNew(ApplicationRequest request) {
        try {
            User user = userInfoUseCase.get(request.name(), request.phone());
            applicationManageUseCase.checkDuplicatedApplication(user, request.recruitmentId());
            return user;
        } catch (UserNotFoundException e) {
            return userManageUseCase.create(request);
        }
    }

    @Override
    public void update(User user, String applicationId, ApplicationRequest request) {
        applicationManageUseCase.checkDuplicatedApplication(user, request.recruitmentId());
        Application application = applicationGetService.find(applicationId);
        applicationManageUseCase.checkReadPermission(user, application);
        applicationUpdateService.from(applicationId, request);
    }

    @Override
    public ApplicationDetailsResponse read(User user, String applicationId) {
        Application application = applicationGetService.find(applicationId);
        applicationManageUseCase.checkReadPermission(user, application);
        return applicationMapper.mapToApplicationDetails(application);
    }

    @Override
    public List<MyApplicationsResponse> readAll(User user) {
        List<Application> applications = applicationGetService.findAll(user);
        return applications.stream()
                .map(application -> {
                    Club club = clubGetService.byId(application.getRecruitment().getClubId());
                    return applicationMapper.mapToMyApplications(application, club);
                }).toList();
    }
}
