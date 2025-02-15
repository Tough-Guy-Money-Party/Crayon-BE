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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    private final ApplicationVerifyService applicationVerifyService;


    @Transactional
    public void apply(ApplicationSaveRequest dto, UUID recruitmentId, User applicant) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        recruitment.checkAvailable();

        applicationVerifyService.checkDuplicate(recruitment.getId(), applicant);

        List<Item> items = itemManageUseCase.create(dto.answers());
        Application application = dto.toApplication(recruitment, applicant);

        applicationSaveService.save(recruitment, application);
        answerSaveService.save(items, application.getId());
    }

    @Transactional(readOnly = true)
    public List<Response> readAll(User applicant) {
        return applicationGetService.findAll(applicant).stream()
                .map(applicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MyApplicationResponse read(String applicationId, User applicant) {
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);

        Answer answer = answerGetService.findByApplicationId(application.getId());

        return MyApplicationResponse.toResponse(application, answer);
    }

    @Transactional
    public void update(String applicationId, ApplicationUpdateRequest dto, User applicant) {
        Application application = applicationGetService.find(applicationId);
        Recruitment recruitment = recruitmentGetService.find(application.getRecruitmentId());
        application.checkAuthorization(applicant);
        recruitment.checkAvailable();

        List<Item> items = itemManageUseCase.create(dto.answers());
        answerUpdateService.update(application.getId(), items);
    }

    @Transactional
    public void delete(String applicationId, User applicant) {
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);
        applicationUpdateService.delete(application);
    }
}
