package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.ProcessResultRepository;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessResultGetService {

    private final ProcessResultRepository processResultRepository;

    public Map<UUID, Status> findAll(Process process, List<Application> applications) {
        List<UUID> applicationIds = applications.stream()
                .map(Application::getId)
                .toList();
        List<ProcessResult> processResults = processResultRepository.findAllByProcessId(process.getId(), applicationIds);
        return processResults.stream()
                .collect(Collectors.toMap(ProcessResult::getApplicationId, ProcessResult::getStatus));
    }

    public List<UUID> findAllPassApplicationIds(Process process) {
        List<ProcessResult> processResults = processResultRepository.findAllPassByProcessId(process.getId());
        return processResults.stream()
                .map(ProcessResult::getApplicationId)
                .toList();
    }

    public ProcessResult findCurrentResult(Application application) {
        Process process = application.getProcess();
        return processResultRepository.findByApplicationIdAndProcessId(application.getId(), process.getId())
                .orElse(ProcessResult.getBeforeEvaluationResult(application.getId(), process.getId()));
    }

    public List<ProcessResult> find(Application application) {
        List<ProcessResult> processResults = processResultRepository.findAllByApplicationId(application.getId());

        long processId = application.getProcess().getId();
        ProcessResult currentProcessResult = processResults.stream()
                .filter(processResult -> processResult.getProcessId() == processId)
                .findFirst()
                .orElse(ProcessResult.getBeforeEvaluationResult(application.getId(), processId));

        if (!processResults.contains(currentProcessResult)) {
            processResults.add(currentProcessResult);
        }

        return processResults;
    }
}
