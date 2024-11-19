package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecruitmentGetService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment find(String recruitmentId) {
        return find(UUID.fromString(recruitmentId));
    }

    public Recruitment find(UUID recruitmentId) {
        return recruitmentRepository.findById(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::new);
    }

    public Page<Recruitment> findAll(Club club, Pageable pageable) {
        return recruitmentRepository.findAllByClub(club, pageable);
    }
}
