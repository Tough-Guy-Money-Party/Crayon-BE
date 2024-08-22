package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.recruitment.application.mapper.ProcessMapper;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.service.ProcessDeleteService;
import com.yoyomo.domain.recruitment.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.*;
import static com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO.*;

@Service
@RequiredArgsConstructor
public class ProcessManageUseCaseImpl implements ProcessManageUseCase {

    private final ProcessSaveService processSaveService;
    private final ApplicationMapper applicationMapper;
    private final ProcessMapper processMapper;
    private final ProcessDeleteService processDeleteService;
    private final RecruitmentGetService recruitmentGetService;

    @Override @Transactional
    public List<Process> save(List<Save> dto, Recruitment recruitment) {
        return processSaveService.saveAll(dto, recruitment);
    }

    @Override
    public List<Response> readAll(String recruitmentId) {
        Recruitment recruitment = recruitmentGetService.find(recruitmentId);

        return recruitment.getProcesses().stream()
                .map(process -> {
                    List<ApplicationResponseDTO.Response> applications = process.getApplications().stream()
                            .map(applicationMapper::toResponse)
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
}
