package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.StageUpdateRequest;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Detail;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class ApplicationManageUseCase {
    private static final int PAGE_SIZE = 100;

    private final UserGetService userGetService;
    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;
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

    public Page<Response> search(String name, UUID recruitmentId, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);

        return applicationGetService.findByName(recruitment, name, pageable)
                .map(applicationMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<Detail> readAll(UUID recruitmentId, Integer stage, Long userId, Pageable pageable) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, stage);

        List<Type> types = recruitmentGetService.findAllTypesByRecruitment(recruitment);

        Page<Application> applications = applicationGetService.findAll(process, pageable);

        List<UUID> applicationIds = applicationGetService.getApplicationIds(applications);

        Map<UUID, Answer> answerMap = answerGetService.findAllApplicationMapByApplicationIds(applicationIds);

        return applications.map(application -> Detail.toDetail(
                application,
                answerMap.get(application.getId()),
                types
        ));
    }

    @Transactional
    public void updateProcess(StageUpdateRequest dto, Long userId, UUID recruitmentId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process process = processGetService.find(recruitment, dto.to());

        dto.ids().stream()
                .map(applicationGetService::find)
                .forEach(application -> application.update(process));
    }

    @Transactional
    public void movePass(UUID recruitmentId, Long fromProcessId, Long toProcessId, Long userId) {
        Recruitment recruitment = checkAuthorityByRecruitmentId(recruitmentId, userId);
        Process from = processGetService.find(fromProcessId);
        Process to = processGetService.find(toProcessId);

        Stream.iterate(0, pageNumber -> pageNumber + 1)
                .map(pageNumber -> applicationGetService.findAll(fromProcessId, pageNumber, PAGE_SIZE))
                .takeWhile(applications -> !applications.isEmpty())
                .forEach(applications -> update(applications, to));

        recruitment.move(to.getType());
    }

    private void update(List<Application> applications, Process to) {
        applications.stream()
                .filter(application -> application.getStatus().isPass())
                .forEach(application -> application.update(to));
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
