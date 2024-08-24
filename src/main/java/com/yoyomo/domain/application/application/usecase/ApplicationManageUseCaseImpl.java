package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.InterviewRequestDTO;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.application.mapper.InterviewMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.ProcessUpdateService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Stage;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCaseImpl implements ApplicationManageUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final UserGetService userGetService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;
    private final AnswerGetService answerGetService;
    private final ProcessGetService processGetService;
    private final ProcessUpdateService processUpdateService;
    private final InterviewMapper interviewMapper;
    private final ApplicationUpdateService applicationUpdateService;

    @Override
    public Detail read(String applicationId, Long userId) {
        Application application = checkAuthorityByApplication(applicationId, userId);
        return applicationMapper.toDetail(application, answerGetService.find(application.getAnswerId()));
    }


    @Override
    public Page<Response> search(String name, String recruitmentId, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);

        List<Response> result = applicationGetService.findByName(recruitment, name).stream()
                .map(applicationMapper::toResponses)
                .toList();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<Detail> readAll(String recruitmentId, Integer stage, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, stage);

        List<Detail> details = process.getApplications().stream()
                .map(application -> applicationMapper.toDetail(application, answerGetService.find(application.getAnswerId())))
                .toList();

        return new PageImpl<>(details, pageable, details.size());
    }

    @Override
    public void update(Stage dto, Long userId, String recruitmentId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);

        dto.ids().stream()
                .map(applicationGetService::find)
                .forEach(application -> {
                    Process from = processGetService.find(recruitment, dto.from());
                    Process to = processGetService.find(recruitment, dto.to());
                    processUpdateService.update(from, to, application);
                });
    }

    @Override
    public void saveInterview(String applicationId, InterviewRequestDTO.Save dto, Long userId) {
        Application application = checkAuthorityByApplication(applicationId, userId);
        Interview from = interviewMapper.from(dto);
        applicationUpdateService.update(application, from);
    }

    private Recruitment checkAuthorityByRecruitmentId(String recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Manager manager = userGetService.find(userId);
        checkAuthority(recruitment.getClub(), manager);

        return recruitment;
    }

    private Application checkAuthorityByApplication(String applicationId, Long userId) {
        Application application = applicationGetService.find(applicationId);
        Manager manager = userGetService.find(userId);
        checkAuthority(application.getProcess().getRecruitment().getClub(), manager);

        return application;
    }
}
