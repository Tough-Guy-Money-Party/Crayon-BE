package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.exception.ProcessNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessGetService {

    private final ProcessRepository processRepository;

    public Process find(Recruitment recruitment, int stage) {
        return processRepository.findByRecruitmentAndStage(recruitment, stage)
                .orElseThrow(ProcessNotFoundException::new);
    }

    public Process find(Long processId) {
        return processRepository.findById(processId)
                .orElseThrow(ProcessNotFoundException::new);
    }

    public List<Process> findAll(Recruitment recruitment) {
        return processRepository.findAllByRecruitment(recruitment);
    }

    public void exists(Long processId) {
        processRepository.findById(processId)
                .orElseThrow(ProcessNotFoundException::new);
    }

    public List<ProcessWithApplicantCount> findAllWithApplicantCount(UUID recruitmentId) {
        return processRepository.findAllWithApplicantCount(recruitmentId);
    }
}
