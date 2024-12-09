package com.yoyomo.domain.application.application.usecase;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;

import com.yoyomo.domain.application.application.dto.request.StageUpdateRequest;
import com.yoyomo.domain.application.application.dto.response.ApplicationListResponse;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCase {

    private final UserGetService userGetService;
    private final ApplicationGetService applicationGetService;
    private final AnswerGetService answerGetService;
    private final ProcessGetService processGetService;
    private final RecruitmentGetService recruitmentGetService;
    private final ClubManagerAuthService clubManagerAuthService;

    @Transactional(readOnly = true)
    public Detail read(String applicationId, Long userId) {
        Application application = checkAuthorityByApplication(applicationId, userId);
        Answer answer = answerGetService.findByApplicationId(application.getId());

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        List<Type> types = recruitmentGetService.findAllTypesByRecruitment(recruitment);

        return Detail.toDetail(application, answer, types);
    }

    public Page<ApplicationListResponse> search(String name, UUID recruitmentId, int stage, long userId,
                                                Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, stage);

        return applicationGetService.findByName(name, process, pageable)
                .map(ApplicationListResponse::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ApplicationListResponse> readAll(UUID recruitmentId, Integer stage, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, stage);

        Page<Application> applications = applicationGetService.findAll(process, pageable);
        return applications.map(ApplicationListResponse::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ApplicationListResponse> readAll(long processId) {
        Process process = processGetService.find(processId);

        List<Application> applications = applicationGetService.findAll(process);
        return applications.stream().map(ApplicationListResponse::toResponse).toList();
    }


    @Transactional
    public void updateProcess(StageUpdateRequest dto, Long userId, UUID recruitmentId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, dto.to());

        dto.ids().stream()
                .map(applicationGetService::find)
                .forEach(application -> application.update(process));
    }

    private Recruitment checkAuthorityByRecruitmentId(UUID recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(recruitment.getId(), manager);

        return recruitment;
    }

    private Application checkAuthorityByApplication(String applicationId, Long userId) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(userId);

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        clubManagerAuthService.checkAuthorization(recruitment.getClub(), manager);
        return application;
    }
}
