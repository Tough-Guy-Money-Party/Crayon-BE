package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
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
public class ProcessManageUseCase {

    private final ProcessSaveService processSaveService;
    private final ApplicationMapper applicationMapper;
    private final ProcessMapper processMapper;
    private final ProcessDeleteService processDeleteService;
    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationGetService applicationGetService;

    public List<Process> save(List<Save> dto, Recruitment recruitment) {
        return processSaveService.saveAll(dto, recruitment);
    }

    /*
        기존 로직이 ApplicationDTO.Detail로 데이터 변환을 하고 있었는데 필요성을 느끼지 못해 Response로 간소화 하여 구현하였음
    */
    public List<Response> readAll(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);

        return recruitment.getProcesses().stream()
                .map(process -> {
                    List<Application> applications = applicationGetService.findAllInStep(recruitment, process.getStage());
                    List<ApplicationResponseDTO.Response> responses = applications.stream()
                            .map(applicationMapper::toResponses)
                            .toList();
                    return processMapper.toResponse(process, responses);
                })
                .sorted(Comparator.comparingInt(Response::stage))
                .toList();
    }

    public List<Process> update(List<Update> dto, Recruitment recruitment) {
        processDeleteService.deleteAll(recruitment.getProcesses());
        recruitment.clearProcesses();

        return dto.stream()
                .map(update -> processSaveService.save(update, recruitment))
                .sorted(Comparator.comparing(Process::getStage))
                .toList();
    }
}
