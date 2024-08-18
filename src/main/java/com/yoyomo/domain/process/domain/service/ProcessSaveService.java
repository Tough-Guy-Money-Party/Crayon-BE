package com.yoyomo.domain.process.domain.service;

import com.yoyomo.domain.process.application.mapper.ProcessMapper;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.process.domain.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO.Save;

@Service
@RequiredArgsConstructor
public class ProcessSaveService {

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;

    public List<Process> saveAll(List<Save> dto) {
        return processRepository.saveAll(processMapper.fromAll(dto));
    }
}
