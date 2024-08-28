package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentDeleteService {

    private final RecruitmentRepository recruitmentRepository;

    public void delete(Recruitment recruitment) {
        recruitmentRepository.delete(recruitment);
    }
}
