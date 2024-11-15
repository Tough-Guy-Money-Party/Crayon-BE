package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.recruitment.exception.ProcessNotFoundException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {

    private final ApplicationRepository applicationRepository;
    private final ProcessRepository processRepository;

    public List<Application> findAll(User user) {
        return applicationRepository.findAllByUserAndDeletedAtIsNull(user);
    }

    public Application find(String id) {
        return applicationRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> findByName(Recruitment recruitment, String name) {
        UUID recruitmentId = recruitment.getId();
        return applicationRepository.findAllByUser_NameAndDeletedAtIsNull(name).stream()
                .filter(application -> application.inRecruitment(recruitmentId))
                .toList();
    }

    public List<Application> findAllInStep(Recruitment recruitment, int stage) {
        Process process = processRepository.findByRecruitmentAndStage(recruitment, stage)
                .orElseThrow(ProcessNotFoundException::new);
        return applicationRepository.findAllByProcess(process);
    }
}
