package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {
    private final RecruitmentRepository recruitmentRepository;

    public Recruitment find(String id) {
        return recruitmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(RecruitmentNotFoundException::new);
    }

    public List<Recruitment> findAll(String clubId) {
        return recruitmentRepository.findAllByClubIdAndDeletedAtIsNull(clubId);
    }
}
