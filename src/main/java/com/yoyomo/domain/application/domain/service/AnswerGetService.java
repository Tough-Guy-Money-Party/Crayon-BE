package com.yoyomo.domain.application.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.application.exception.AnswerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerGetService {

	private final AnswerRepository answerRepository;

	public Answer findByApplicationId(UUID applicationId) {
		return answerRepository.findByApplicationId(applicationId.toString())
			.orElseThrow(AnswerNotFoundException::new);
	}
}
