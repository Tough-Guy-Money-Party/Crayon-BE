package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.form.domain.repository.dto.LinkedRecruitment;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Map<String, List<LinkedRecruitment>> findAllLinkedRecruitments(List<String> formIds) {
        return recruitmentRepository.findByForms(formIds).stream()
                .collect(Collectors.groupingBy(LinkedRecruitment::formId));
    }
}
