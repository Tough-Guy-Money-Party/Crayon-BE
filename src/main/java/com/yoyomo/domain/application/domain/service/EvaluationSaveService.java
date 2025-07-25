package com.yoyomo.domain.application.domain.service;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.domain.entity.Evaluation;
import com.yoyomo.domain.application.domain.repository.EvaluationRepository;
import com.yoyomo.domain.application.exception.EvaluationAlreadyExistException;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluationSaveService {

	private final EvaluationRepository evaluationRepository;

	public Evaluation save(User manager, Evaluation evaluation) {
		if (evaluationRepository.existsByManagerAndApplication(manager, evaluation.getApplication())) {
			throw new EvaluationAlreadyExistException();
		}
		return evaluationRepository.save(evaluation);
	}
}
