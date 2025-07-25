package com.yoyomo.domain.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.EvaluationMemo;
import com.yoyomo.domain.application.domain.repository.EvaluationMemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluationMemoGetService {

	private final EvaluationMemoRepository evaluationMemoRepository;

	public List<EvaluationMemo> findAll(Application application) {
		return evaluationMemoRepository.findAllByApplication(application);
	}
}
