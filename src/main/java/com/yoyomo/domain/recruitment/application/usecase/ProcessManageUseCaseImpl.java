package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.application.application.dto.response.EvaluationResponseDTO;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.application.mapper.EvaluationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.AnswerGetService;
import com.yoyomo.domain.recruitment.application.mapper.ProcessMapper;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.ProcessDeleteService;
import com.yoyomo.domain.recruitment.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Update;
import static com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class ProcessManageUseCaseImpl implements ProcessManageUseCase {

    private final ProcessSaveService processSaveService;
    private final ApplicationMapper applicationMapper;
    private final ProcessMapper processMapper;
    private final ProcessDeleteService processDeleteService;
    private final RecruitmentGetService recruitmentGetService;
    private final AnswerGetService answerGetService;
    private final EvaluationMapper evaluationMapper;

    @Override
    public List<Process> save(List<Save> dto, Recruitment recruitment) {
        return processSaveService.saveAll(dto, recruitment);
    }

    /*
        해당 DTO는 List<ApplicationResponseDTO.Response> applications를 지니고 있음에도 해당 메서드는 .Detail을 사용해 매핑하고 있음
        해당 로직이 왜 필요한지가 불분명해 일단 주석처리 하고 아래 새로운 메서드를 구현하였음
    */
//    @Override
//    public List<Response> readAll(String recruitmentId) {
//        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
//
//        return recruitment.getProcesses().stream()
//                .map(process -> {
//                    List<ApplicationResponseDTO.Detail> applications = process.getApplications().stream()
//                            .map(application -> applicationMapper.toDetail(application, answerGetService.find(application.getAnswerId()), getEvaluations(application)))
//                            .toList();
//
//                    return processMapper.toResponse(process, applications);
//                })
//                .sorted(Comparator.comparingInt(Response::stage))
//                .toList();
//    }

    @Override
    public List<Response> readAll(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);

        return recruitment.getProcesses().stream()
                .map(process -> {
                    List<ApplicationResponseDTO.Response> applications = process.getApplications().stream()
                            .map(applicationMapper::toResponses)
                            .toList();

                    return processMapper.toResponse(process, applications);
                })
                .sorted(Comparator.comparingInt(Response::stage))
                .toList();
    }

    @Override
    public List<Process> update(List<Update> dto, Recruitment recruitment) {
        processDeleteService.deleteAll(recruitment.getProcesses());
        recruitment.clearProcesses();

        return dto.stream()
                .map(update -> processSaveService.save(update, recruitment))
                .sorted(Comparator.comparing(Process::getStage))
                .toList();
    }

    // 얜 여기 왜 있음??
    private List<EvaluationResponseDTO.Response> getEvaluations(Application application) {
        return application.getEvaluations().stream()
                .map(evaluationMapper::toResponse)
                .toList();
    }
}
