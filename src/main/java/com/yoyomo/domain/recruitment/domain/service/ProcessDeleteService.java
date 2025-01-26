package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessDeleteService {

    private final ProcessRepository processRepository;

    public void deleteAll(List<Process> processes) {
        processRepository.deleteAllInBatch(processes);
    }

    public void deleteAllByRecruitment(Recruitment recruitment) {
        processRepository.deleteAllByRecruitment(recruitment);
    }
}
