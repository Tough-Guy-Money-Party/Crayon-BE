package com.yoyomo.domain.application.domain.service;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluationMemoSaveService {

	private final EvaluationMemoRepository evaluationMemoRepository;

	public EvaluationMemo save(EvaluationMemo memo) {
		return evaluationMemoRepository.save(memo);
	}
}
