package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationSaveService {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public Application save(UUID recruitmentId, Application application) {
        recruitmentRepository.increaseApplicantCount(recruitmentId);
        return applicationRepository.save(application);
    }
}
