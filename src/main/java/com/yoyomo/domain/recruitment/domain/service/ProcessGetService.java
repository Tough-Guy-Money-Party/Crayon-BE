package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.exception.ProcessNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessGetService {

    private final ProcessRepository processRepository;

    public Process find(String id) {
        return processRepository.findById(UUID.fromString(id))
                .orElseThrow(ProcessNotFoundException::new);
    }
}
