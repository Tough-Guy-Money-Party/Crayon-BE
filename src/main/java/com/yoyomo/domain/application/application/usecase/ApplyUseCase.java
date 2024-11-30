package com.yoyomo.domain.application.application.usecase;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Update;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.application.domain.service.AnswerSaveService;
import com.yoyomo.domain.application.domain.service.AnswerUpdateService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.application.domain.service.ApplicationUpdateService;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.UserGetService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    @Transactional
    public void apply(Save dto, String recruitmentId, Long userId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        recruitment.checkAvailable();

        User applicant = userGetService.find(userId);

        List<Item> items = itemManageUseCase.create(dto.answers());
        Application application = dto.toApplication(recruitment, applicant);

        applicationSaveService.save(recruitment, application); //todo application.answerId 추가
        answerSaveService.save(items, application.getId());
    }

    public List<Response> readAll(Long userId) {
        User applicant = userGetService.find(userId);
        return applicationGetService.findAll(applicant).stream()
                .map(applicationMapper::toResponse)
                .toList();
    }

    public MyResponse read(String applicationId, Long userId) {
        User applicant = userGetService.find(userId);
        Application application = applicationGetService.find(applicationId);
        application.checkAuthorization(applicant);

        Answer answer = answerGetService.findByApplicationId(application.getId());

        return applicationMapper.toMyResponse(application, answer);
    }

    @Transactional
    public void update(String applicationId, Update dto, Long userId) {
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
