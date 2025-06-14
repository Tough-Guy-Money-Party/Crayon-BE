package com.yoyomo.domain.application.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationMemoDeleteService {
	private final EvaluationMemoRepository evaluationMemoRepository;

	public void deleteByRecruitmentId(UUID recruitmentId) {
		evaluationMemoRepository.deleteByRecruitmentId(recruitmentId);
	}
}
