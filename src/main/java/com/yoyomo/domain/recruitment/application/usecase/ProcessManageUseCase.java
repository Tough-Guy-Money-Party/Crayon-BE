package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.domain.service.ProcessDeleteService;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Update;
import static com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO.Response;

@Service
@RequiredArgsConstructor
public class ProcessManageUseCase {

    private final ProcessSaveService processSaveService;
    private final ProcessDeleteService processDeleteService;
    private final RecruitmentGetService recruitmentGetService;
    private final ApplicationGetService applicationGetService;
    private final ClubManagerAuthService clubManagerAuthService;
    private final ProcessGetService processGetService;

    public List<Process> save(List<Save> dto, Recruitment recruitment) {
        return processSaveService.saveAll(dto, recruitment);
    }

    /*
        기존 로직이 ApplicationDTO.Detail로 데이터 변환을 하고 있었는데 필요성을 느끼지 못해 Response로 간소화 하여 구현하였음
    */
    @Transactional(readOnly = true)
    public List<Response> readAll(UUID recruitmentId, long userId) {
        clubManagerAuthService.checkAuthorization(recruitmentId, userId);
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        List<Process> processes = processGetService.findAll(recruitment);
        Map<Process, Long> processApplicantCount = applicationGetService.countInProcesses(recruitment.getId(),
                processes);

        return processes.stream()
                .map(process -> Response.toResponse(process, processApplicantCount.getOrDefault(process, 0L),
                        process.getProcessStep()))
                .sorted(Comparator.comparingInt(Response::stage))
                .toList();
    }

    @Transactional
    public List<Process> update(List<Update> dto, Recruitment recruitment) {
        processDeleteService.deleteAll(recruitment.getProcesses());
        recruitment.clearProcesses();

        return dto.stream()
                .map(update -> processSaveService.save(update, recruitment))
                .sorted(Comparator.comparing(Process::getStage))
                .toList();
    }

    @Transactional
    public void updateStep(UUID recruitmentId, Long processId, ProcessStep step, Long userId) {
        clubManagerAuthService.checkAuthorization(recruitmentId, userId);

        Recruitment recruitment = recruitmentGetService.find(recruitmentId);
        Process process = processGetService.find(processId);

        Type currentProcess = recruitment.getCurrentProcess();

        process.checkMovable(currentProcess, step);

        process.updateStep(step);
    }
}

