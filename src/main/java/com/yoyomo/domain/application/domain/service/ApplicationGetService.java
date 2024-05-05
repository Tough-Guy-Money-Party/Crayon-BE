package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
    private final ApplicationRepository applicationRepository;

    public Application find(String applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> findAll(String recruitmentId) {
        return applicationRepository.findAllByRecruitmentId(recruitmentId);
    }

    public List<Application> findAllByApplicantName(String recruitmentId, String name) {
        return applicationRepository.findAllByRecruitmentIdAndApplicant_NameContaining(recruitmentId, name);
    }

    public List<Application> findAll(Applicant applicant) {
        return applicationRepository.findAllByApplicant(applicant);
    }

    public boolean exists(Applicant applicant, String recruitmentId) {
        return applicationRepository.existsByApplicantAndRecruitment_Id(applicant, recruitmentId);
    }
}
