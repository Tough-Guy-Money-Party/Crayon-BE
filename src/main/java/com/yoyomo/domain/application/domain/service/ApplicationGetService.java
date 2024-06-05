package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.dto.res.ApplicantInfoDTO;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.shared.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
    private final ApplicationRepository applicationRepository;

    public Application find(String applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> findAll(String recruitmentId, int pageNum) {
        return applicationRepository.findAllByRecruitmentId(recruitmentId, PageUtil.makePageObject(pageNum));
    }

    public List<Application> findAllByApplicantName(String recruitmentId, String name, int pageNum) {

        return applicationRepository.findAllByRecruitmentIdAndApplicant_NameContaining(recruitmentId, name, PageUtil.makePageObject(pageNum));
    }

    public List<ApplicantInfoDTO> findApplicantsByStage(String recruitmentId, int applicationStage) {
        return applicationRepository.findAllByRecruitmentIdAndApplicationStage(recruitmentId, applicationStage)
                .stream()
                .map(application -> new ApplicantInfoDTO(
                        application.getId(),
                        application.getApplicant().getName()
                ))
                .collect(Collectors.toList());
    }

    public List<Application> findAll(Applicant applicant, int pageNum) {
        return applicationRepository.findAllByApplicant(applicant, PageUtil.makePageObject(pageNum));
    }

    public boolean exists(Applicant applicant, String recruitmentId) {
        return applicationRepository.existsByApplicantAndRecruitment_Id(applicant, recruitmentId);
    }
}
