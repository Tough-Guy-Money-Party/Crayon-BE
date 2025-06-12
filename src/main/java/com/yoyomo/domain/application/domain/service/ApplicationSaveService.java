package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationJdbcRepository;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.vo.ApplicationReply;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationSaveService {

    private final ApplicationJdbcRepository applicationJdbcRepository;
    private final ApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;

    public Application save(UUID recruitmentId, Application application) {
        recruitmentRepository.increaseApplicantCount(recruitmentId);
        return applicationRepository.save(application);
    }

    public List<Application> saveAll(UUID recruitmentId, List<ApplicationReply> applicationReplies) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);
        Process process = recruitment.getDocumentProcess();

        List<Application> applications = applicationReplies.stream()
                .map(ar -> ar.toApplication(recruitmentId, process))
                .toList();

        recruitmentRepository.increaseApplicantCount(recruitmentId, applicationReplies.size());
        return applicationJdbcRepository.batchInsert(applications);
    }
}
