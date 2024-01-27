package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
    private final ApplicationRepository applicationRepository;

    public Application find(String applicationsId) {
        return applicationRepository.findById(applicationsId)
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> findAll(String recruitmentId) {
        return applicationRepository.findAllByRecruitmentId(recruitmentId);
    }
}
