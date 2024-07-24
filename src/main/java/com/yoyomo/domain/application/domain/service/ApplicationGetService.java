package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.dto.res.ApplicantInfoDTO;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.shared.util.PageUtil;
import com.yoyomo.domain.user.domain.entity.Applicant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Application> findAll(String recruitmentId, Integer stage, Pageable pageable) {
        return applicationRepository.findAllByRecruitmentIdAndCurrentStage(recruitmentId, stage, pageable);
    }

    public List<Application> findAllByApplicantName(String recruitmentId, String name, int pageNum) {

        return applicationRepository.findAllByRecruitmentIdAndApplicant_NameContaining(recruitmentId, name, PageUtil.makePageObject(pageNum));
    }

    public List<ApplicantInfoDTO> findApplicantsByStage(String recruitmentId, int currentStage) {
        return applicationRepository.findAllByRecruitmentIdAndCurrentStage(recruitmentId, currentStage)
                .stream()
                .map(application -> new ApplicantInfoDTO(
                        application.getId(),
                        application.getApplicant().getName()
                ))
                .collect(Collectors.toList());
    }

    public int getTotalApplicantsCount(String recruitmentId) {
        return applicationRepository.countByRecruitmentId(recruitmentId);
    }

    public int getAcceptedApplicantsCount(String recruitmentId) {
        return applicationRepository.countByRecruitmentIdAndCurrentStageGreaterThanEqual(recruitmentId, 0);
    }

    public int getRejectedApplicantsCount(String recruitmentId) {
        return applicationRepository.countByRecruitmentIdAndCurrentStageLessThan(recruitmentId, 0);
    }

    public int getAverageRating(String applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
        application.getAssessments();
        return 0;
    }

    public List<Application> findAll(Applicant applicant, int pageNum) {
        return applicationRepository.findAllByApplicant(applicant, PageUtil.makePageObject(pageNum));
    }

    public boolean exists(Applicant applicant, String recruitmentId) {
        return applicationRepository.existsByApplicantAndRecruitmentId(applicant, recruitmentId);
    }

    public Application find(Recruitment recruitment, String name, String phone, String email) {
        return applicationRepository.findByRecruitmentIdAndApplicant_NameAndApplicant_PhoneAndApplicant_Email(recruitment.getId(), name, phone, email)
                .orElseThrow(ApplicationNotFoundException::new);
    }
}
