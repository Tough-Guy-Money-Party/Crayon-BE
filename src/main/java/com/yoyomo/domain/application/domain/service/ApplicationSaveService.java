package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationSaveService {

    private final ApplicationRepository applicationRepository;

    public Application save(Recruitment recruitment, Application application) {
        recruitment.plusApplicantsCount();
        return applicationRepository.save(application);
    }
}
