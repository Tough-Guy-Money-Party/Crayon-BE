package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessDeleteService {

    private final ProcessRepository processRepository;

    public void deleteAll(List<Process> processes) {
        processRepository.deleteAllInBatch(processes);
    }
}
