package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.mapper.AnswerMapper;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerSaveService;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.item.application.usecase.ItemManageUseCase;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.application.mapper.UserMapper;
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
    private final AnswerMapper answerMapper;
    private final ApplicationMapper applicationMapper;
    private final ItemManageUseCase itemManageUseCase;
    private final ApplicationSaveService applicationSaveService;
    private final AnswerSaveService answerSaveService;
    private final ApplicationGetService applicationGetService;
    private final UserMapper userMapper;

    @Override @Transactional
    public void apply(Save dto, String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Process process = recruitment.getProcesses().get(0);
        List<Item> items = itemManageUseCase.create(dto.answers());
        Application application = applicationSaveService.save(applicationMapper.from(dto, process));
        Answer answer = answerSaveService.save(answerMapper.from(items, application.getId().toString()));
        application.mapToAnswer(answer.getId());
        process.addApplication(application);
    }

    @Override
    public List<MyResponse> readAll(Find dto) {
        return applicationGetService.findAll(userMapper.from(dto)).stream()
                .map(applicationMapper::toMyResponses)
                .toList();
    }
}
