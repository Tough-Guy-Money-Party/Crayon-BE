package com.yoyomo.domain.recruitment.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentSaveService {
	private final RecruitmentRepository recruitmentRepository;

	public Recruitment save(Recruitment recruitment) {
		return recruitmentRepository.save(recruitment);
	}

	public List<Recruitment> saveAll(List<Recruitment> recruitments) {
		return recruitmentRepository.saveAll(recruitments);
	}
}
