package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.recruitment.application.dto.request.RecruitmentRequestDTO.Save;
import com.yoyomo.domain.recruitment.application.mapper.RecruitmentMapper;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentSaveService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentMapper recruitmentMapper;

    public Recruitment save(Save dto, Club club) {
        return recruitmentRepository.save(recruitmentMapper.from(dto, club));
    }

    public Recruitment save(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }
}
