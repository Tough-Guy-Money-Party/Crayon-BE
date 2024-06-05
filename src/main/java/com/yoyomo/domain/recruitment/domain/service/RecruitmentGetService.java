package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {
    private final RecruitmentRepository recruitmentRepository;

    public Recruitment find(String id) {
        return recruitmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(RecruitmentNotFoundException::new);
    }

    public LocalDate getRecruitmentEndDate(String id) {
        Recruitment recruitment = recruitmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(RecruitmentNotFoundException::new);
        int lastIndex = recruitment.getProcesses().size() - 1;
        return recruitment.getProcesses().get(lastIndex).getEndAt();
    }

    public List<Recruitment> findAll(String clubId) {
        return recruitmentRepository.findAllByClubIdAndDeletedAtIsNull(clubId);
    }
}
