package com.yoyomo.domain.process.domain.service;

import com.yoyomo.domain.process.application.mapper.ProcessMapper;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.process.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO.Save;
import static com.yoyomo.domain.process.application.dto.request.ProcessRequestDTO.Update;

@Service
@RequiredArgsConstructor
public class ProcessSaveService {

    private final ProcessRepository processRepository;
    private final ProcessMapper processMapper;

    public List<Process> saveAll(List<Save> dto, Recruitment recruitment) {
        return processRepository.saveAll(
                dto.stream()
                        .map(save -> processMapper.from(save, recruitment))
                        .toList()
        );
    }

    public Process save(Update dto, Recruitment recruitment) {
        return processRepository.save(processMapper.from(dto, recruitment));
    }
}
