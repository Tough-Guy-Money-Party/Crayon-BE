package com.yoyomo.domain.recruitment.domain.service;

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
}
