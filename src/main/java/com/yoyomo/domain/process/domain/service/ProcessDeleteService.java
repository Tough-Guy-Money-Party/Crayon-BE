package com.yoyomo.domain.process.domain.service;

import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.process.domain.repository.ProcessRepository;
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
