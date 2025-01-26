package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.repository.ProcessResultRepository;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessResultSaveService {

    private final ProcessResultRepository processResultRepository;

    public ProcessResult save(Application application, Process process) {
        ProcessResult processResult = ProcessResult.builder()
                .applicationId(application.getId())
                .processId(process.getId())
                .build();

        return processResultRepository.save(processResult);
    }
}
