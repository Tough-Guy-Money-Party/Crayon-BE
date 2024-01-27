package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
    private final ApplicationRepository applicationRepository;

    public Application find(String applicationsId) {
        return applicationRepository.findById(applicationsId)
                .orElseThrow(ApplicationNotFoundException::new);
    }
}
