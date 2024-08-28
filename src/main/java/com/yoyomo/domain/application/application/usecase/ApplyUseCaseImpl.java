package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Update;
import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.Response;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.*;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;
import static com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO.MyResponse;
import static com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;

@Service
@RequiredArgsConstructor
public class ApplyUseCaseImpl implements ApplyUseCase {

    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationMapper applicationMapper;
    private final ItemManageUseCase itemManageUseCase;
    private final ApplicationSaveService applicationSaveService;
    private final AnswerSaveService answerSaveService;
    private final ApplicationGetService applicationGetService;
    private final AnswerGetService answerGetService;
    private final AnswerUpdateService answerUpdateService;
    private final ApplicationUpdateService applicationUpdateService;


    @Override @Transactional
    public void apply(Save dto, String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Process process = recruitment.getProcesses().get(0);
        List<Item> items = itemManageUseCase.create(dto.answers());
        Application application = applicationSaveService.save(dto, process);
        Answer answer = answerSaveService.save(items, application);
        application.mapToAnswer(answer.getId());
        process.addApplication(application);
        recruitment.plusApplicantsCount();
    }

    @Override
    public List<Response> readAll(Find dto) {
        return applicationGetService.findAll(dto).stream()
                .map(applicationMapper::toResponses)
                .toList();
    }

    @Override
    public MyResponse read(String applicationId) {
        Application application = applicationGetService.find(applicationId);
        return applicationMapper.toMyResponse(application, answerGetService.find(application.getAnswerId()));
    }

    @Override
    public void update(String applicationId, Update dto) {
        Application application = applicationGetService.find(applicationId);
        List<Item> items = itemManageUseCase.create(dto.answers());
        answerUpdateService.from(application.getAnswerId(), items);
    }

    @Override @Transactional
    public void delete(String applicationId) {
        Application application = applicationGetService.find(applicationId);
        Recruitment recruitment = application.getProcess().getRecruitment();
        recruitment.minusApplicantsCount();
        applicationUpdateService.delete(application);
    }

}
