package com.yoyomo.domain.recruitment.domain.service;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Save;
import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.Update;

import com.yoyomo.domain.recruitment.application.mapper.ProcessMapper;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void saveAll(List<Process> processes) {
        processRepository.saveAll(processes);
    }

    public Process save(Update dto, Recruitment recruitment) {
        return processRepository.save(processMapper.from(dto, recruitment));
    }
}
