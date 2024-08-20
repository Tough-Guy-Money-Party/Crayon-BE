package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment find(String recruitmentId) {
        return recruitmentRepository.findById(UUID.fromString(recruitmentId))
                .orElseThrow(RecruitmentNotFoundException::new);
    }
}
