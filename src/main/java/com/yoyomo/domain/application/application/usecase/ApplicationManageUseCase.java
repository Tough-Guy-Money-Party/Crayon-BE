package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationMoveRequest;
import com.yoyomo.domain.application.application.dto.request.StageUpdateRequest;
import com.yoyomo.domain.application.application.dto.response.ApplicationDetailResponse;
import com.yoyomo.domain.application.application.dto.response.ApplicationListResponse;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.domain.service.ProcessResultGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCase {

    private final UserGetService userGetService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationUpdateService applicationUpdateService;
    private final AnswerGetService answerGetService;
    private final ProcessGetService processGetService;
    private final RecruitmentGetService recruitmentGetService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final ProcessResultGetService processResultGetService;

    @Transactional(readOnly = true)
    public ApplicationDetailResponse read(String applicationId, Long userId) {
        Application application = checkAuthorityByApplication(applicationId, userId);
        Answer answer = answerGetService.findByApplicationId(application.getId());

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        List<Type> types = recruitmentGetService.findAllTypesByRecruitment(recruitment);
        ProcessResult processResult = processResultGetService.findCurrentResult(application);

        return ApplicationDetailResponse.toResponse(application, answer, types, processResult);
    }

    public Page<ApplicationListResponse> search(String name, UUID recruitmentId, int stage, long userId,
                                                Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, stage);

        Page<Application> applications = applicationGetService.findByName(name, process, pageable);
        Map<UUID, Status> processResults = processResultGetService.findAll(process, applications.getContent());

        return ApplicationListResponse.toResponse(applications, processResults);
    }

    @Transactional(readOnly = true)
    public Page<ApplicationListResponse> readAll(UUID recruitmentId, int stage, long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, stage);

        Page<Application> applications = applicationGetService.findAll(process, pageable);
        Map<UUID, Status> processResults = processResultGetService.findAll(process, applications.getContent());

        return ApplicationListResponse.toResponse(applications, processResults);
    }

    @Transactional(readOnly = true)
    public List<ApplicationListResponse> readAll(Long processId, Long userId) {
        Process process = checkAuthorityByProcessId(processId, userId);
        List<Application> applications = applicationGetService.findAll(process);
        Map<UUID, Status> processResults = processResultGetService.findAll(process, applications);

        return ApplicationListResponse.toResponse(applications, processResults);
    }


    @Transactional
    public void updateProcess(StageUpdateRequest dto, Long userId, UUID recruitmentId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, dto.to());

        dto.ids().stream()
                .map(applicationGetService::find)
                .forEach(application -> application.update(process));
    }

    /*
    todo 후에 확장된다면 전략 패턴으로 리팩토링
     */
    @Transactional
    public void moveApplicant(UUID recruitmentId, ApplicationMoveRequest dto, Long userId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);

        Process from = processGetService.find(recruitment, dto.fromStage());
        Process to = processGetService.find(recruitment, dto.toStage());

        List<UUID> passApplicationIds = processResultGetService.findAllPassApplicationIds(from);
        applicationUpdateService.updatePassApplicants(passApplicationIds, to);

        recruitment.updateProcess(to.getType());
    }

    private Recruitment checkAuthorityByRecruitmentId(UUID recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        User manager = userGetService.find(userId);
        clubManagerAuthService.checkAuthorization(recruitment.getId(), manager);

        return recruitment;
    }

    private Process checkAuthorityByProcessId(Long processId, long userId) {
        Process process = processGetService.find(processId);
        clubManagerAuthService.checkAuthorization(process, userId);

        return process;
    }

    private Application checkAuthorityByApplication(String applicationId, Long userId) {
        Application application = applicationGetService.find(applicationId);
        User manager = userGetService.find(userId);

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        clubManagerAuthService.checkAuthorization(recruitment.getClub(), manager);
        return application;
    }
}
