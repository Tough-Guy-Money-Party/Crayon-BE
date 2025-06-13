package com.yoyomo.domain.application.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.domain.repository.EvaluationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationDeleteService {
	private final EvaluationRepository evaluationRepository;

	public void deleteEvaluationByRecruitmentId(UUID recruitmentId) {
		evaluationRepository.deleteByRecruitmentId(recruitmentId);
	}
}
