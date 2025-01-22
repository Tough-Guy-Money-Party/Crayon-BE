package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationSaveRequest;
import com.yoyomo.domain.application.application.dto.request.ApplicationUpdateRequest;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import com.yoyomo.domain.application.application.dto.response.MyApplicationResponse;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.AnswerSaveService;
import com.yoyomo.domain.application.domain.service.AnswerUpdateService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.application.domain.service.ApplicationVerifyService;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationMapper applicationMapper;
    private final ApplicationSaveService applicationSaveService;
    private final AnswerSaveService answerSaveService;
    private final ApplicationGetService applicationGetService;
    private final AnswerGetService answerGetService;
    private final AnswerUpdateService answerUpdateService;
    private final ApplicationUpdateService applicationUpdateService;
    private final ItemManageUseCase itemManageUseCase; // todo 삭제
    private final UserGetService userGetService;
    private final ApplicationVerifyService applicationVerifyService;


    @Transactional
    public void apply(ApplicationSaveRequest dto, UUID recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        recruitment.checkAvailable();

        User applicant = userGetService.find(userId);
        applicationVerifyService.checkConflict(recruitment.getId(), applicant);

        List<Item> items = itemManageUseCase.create(dto.answers());
        Application application = dto.toApplication(recruitment, applicant);

        applicationSaveService.save(recruitment, application);
        Answer answer = answerSaveService.save(items, application.getId());
        application.addAnswer(answer.getId());
    }

    @Transactional(readOnly = true)
    public List<Response> readAll(long userId) {
        User applicant = userGetService.find(userId);
        return applicationGetService.findAll(applicant).stream()
                .map(applicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MyApplicationResponse read(String applicationId, Long userId) {
        User applicant = userGetService.find(userId);
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);

        Answer answer = answerGetService.findByApplicationId(application.getId());

        return MyApplicationResponse.toResponse(application, answer);
    }

    @Transactional
    public void update(String applicationId, ApplicationUpdateRequest dto, Long userId) {
        User applicant = userGetService.find(userId);
        Application application = applicationGetService.find(applicationId);
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        application.checkAuthorization(applicant);
        recruitment.checkAvailable();

        List<Item> items = itemManageUseCase.create(dto.answers());
        answerUpdateService.update(application.getId(), items);
    }

    @Transactional
    public void delete(String applicationId, Long userId) {
        User applicant = userGetService.find(userId);
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);
        applicationUpdateService.delete(application);
    }
}
