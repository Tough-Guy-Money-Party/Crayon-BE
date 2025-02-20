package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import com.yoyomo.global.common.TransactionSupporter;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationSaveService {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TransactionSupporter transactionSupporter;

    @Transactional
    public Application save(Recruitment recruitment, Application application) {
        try {
            recruitment.plusApplicantsCount();
            recruitmentRepository.save(recruitment);
        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
            retry(recruitment);
        }
        return applicationRepository.save(application);
    }

    private void retry(Recruitment recruitment) {
        try {
            transactionSupporter.executeNewTransaction(() -> {
                Recruitment lockedRecruitment = recruitmentRepository.findByIdWithOptimisticLock(recruitment.getId())
                        .orElseThrow(RecruitmentNotFoundException::new);
                lockedRecruitment.plusApplicantsCount();
            });
        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
            log.error("too much update");
        }
    }
}
