package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.EvaluationResponseDTO;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.application.mapper.EvaluationMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.EvaluationGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Stage;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCase {

    private final UserGetService userGetService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;
    private final AnswerGetService answerGetService;
    private final ProcessGetService processGetService;
    private final RecruitmentGetService recruitmentGetService;
    private final EvaluationGetService evaluationGetService;
    private final EvaluationMapper evaluationMapper;
    private final ClubManagerAuthService clubManagerAuthService;

    public Detail read(String applicationId, Long userId) {
        Application application = checkAuthorityByApplication(applicationId, userId);
        List<EvaluationResponseDTO.Response> evaluations = getEvaluations(application);
        Answer answer = answerGetService.findByApplicationId(applicationId);

        return applicationMapper.toDetail(application, answer, evaluations);
    }

    public Page<Response> search(String name, String recruitmentId, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);

        List<Application> applications = applicationGetService.findByName(recruitment, name);
        List<Response> result = applications.stream()
                .map(applicationMapper::toResponses)
                .toList();

        return new PageImpl<>(result, pageable, result.size());
    }

    public Page<Detail> readAll(String recruitmentId, int stage, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        List<Application> applications = applicationGetService.findAllInStep(recruitment, stage);

        List<Detail> details = applications.stream()
                .map(application -> applicationMapper.toDetail(application, answerGetService.findByApplicationId(application.getId().toString()), getEvaluations(application)))
                .toList();

        return new PageImpl<>(details, pageable, details.size());
    }

    @Transactional
    public void updateProcess(Stage dto, Long userId, String recruitmentId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, dto.to());

        dto.ids().stream()
                .map(applicationGetService::find)
                .forEach(application -> application.update(process));
    }

    private Recruitment checkAuthorityByRecruitmentId(String recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Manager manager = userGetService.find(userId);
        recruitment.getClub().checkAuthority(manager);

        return recruitment;
    }

    private Application checkAuthorityByApplication(String applicationId, Long userId) {
        Application application = applicationGetService.find(applicationId);
        Manager manager = userGetService.find(userId);

        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        clubManagerAuthService.checkAuthorization(recruitment.getClub(), manager);
        return application;
    }

    private List<EvaluationResponseDTO.Response> getEvaluations(Application application) {
        return evaluationGetService.findAll(application.getId()).stream()
                .filter(evaluation -> evaluation.getDeletedAt() == null)
                .map(evaluationMapper::toResponse)
                .toList();
    }
}
