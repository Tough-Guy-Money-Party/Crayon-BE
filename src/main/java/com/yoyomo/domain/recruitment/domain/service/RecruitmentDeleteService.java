package com.yoyomo.domain.recruitment.domain.service;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentDeleteService {

	private final RecruitmentRepository recruitmentRepository;

	public void delete(Recruitment recruitment) {
		recruitmentRepository.delete(recruitment);
	}
}
