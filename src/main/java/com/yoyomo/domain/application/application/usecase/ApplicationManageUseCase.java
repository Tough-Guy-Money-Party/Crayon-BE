package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationMoveRequest;
import com.yoyomo.domain.application.application.dto.request.StageUpdateRequest;
import com.yoyomo.domain.application.application.dto.response.ApplicantsResponse;
import com.yoyomo.domain.application.application.dto.response.ApplicationDetailResponse;
import com.yoyomo.domain.application.application.dto.response.ApplicationListResponse;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
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

    private final ApplicationGetService applicationGetService;
    private final ApplicationUpdateService applicationUpdateService;
    private final AnswerGetService answerGetService;
    private final ProcessGetService processGetService;
    private final RecruitmentGetService recruitmentGetService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final ProcessResultGetService processResultGetService;

    @Transactional(readOnly = true)
    public ApplicationDetailResponse read(String applicationId, User user) {
        Application application = checkAuthorityByApplication(applicationId, user);
        Answer answer = answerGetService.findByApplicationId(application.getId());

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        List<Type> types = recruitmentGetService.findAllTypesByRecruitment(recruitment);
        ProcessResult processResult = processResultGetService.findCurrentResult(application);

        return ApplicationDetailResponse.toResponse(application, answer, types, processResult);
    }

    public Page<ApplicationListResponse> search(String name, UUID recruitmentId, int stage, User user,
                                                Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, user);
        Process process = processGetService.find(recruitment, stage);

        Page<Application> applications = applicationGetService.findByName(name, process, pageable);
        Map<UUID, Status> processResults = processResultGetService.findAll(process, applications.getContent());

        return ApplicationListResponse.toResponse(applications, processResults);
    }

    @Transactional(readOnly = true)
    public Page<ApplicationListResponse> readAll(UUID recruitmentId, int stage, User user, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, user);
        Process process = processGetService.find(recruitment, stage);

        Page<ApplicationWithStatus> applicationWithStatus = applicationGetService.findAll(process, pageable);

        return ApplicationListResponse.toResponse(applicationWithStatus);
    }

    @Transactional(readOnly = true)
    public List<ApplicantsResponse> readAll(Long processId, User user) {
        Process process = checkAuthorityByProcessId(processId, user);
        List<Application> applications = applicationGetService.findAllOrderByName(process);

        return ApplicantsResponse.toResponse(applications);
    }


    @Transactional
    public void updateProcess(StageUpdateRequest dto, User user, UUID recruitmentId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, user);
        Process process = processGetService.find(recruitment, dto.to());

        dto.ids().stream()
                .map(applicationGetService::find)
                .forEach(application -> application.update(process));
    }

    /*
    todo 후에 확장된다면 전략 패턴으로 리팩토링
     */
    @Transactional
    public void moveApplicant(UUID recruitmentId, ApplicationMoveRequest dto, User user) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, user);

        Process from = processGetService.find(recruitment, dto.fromStage());
        Process to = processGetService.find(recruitment, dto.toStage());

        List<UUID> passApplicationIds = processResultGetService.findAllPassApplicationIds(from);
        applicationUpdateService.updatePassApplicants(passApplicationIds, to);

        recruitment.updateProcess(to.getType());
    }

    private Recruitment checkAuthorityByRecruitmentId(UUID recruitmentId, User manager) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        clubManagerAuthService.checkAuthorization(recruitment.getId(), manager);

        return recruitment;
    }

    private Process checkAuthorityByProcessId(Long processId, User user) {
        Process process = processGetService.find(processId);
        clubManagerAuthService.checkAuthorization(process, user);

        return process;
    }

    private Application checkAuthorityByApplication(String applicationId, User manager) {
        Application application = applicationGetService.find(applicationId);

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        clubManagerAuthService.checkAuthorization(recruitment.getClub(), manager);
        return application;
    }
}
