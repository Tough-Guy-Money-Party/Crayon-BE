package com.yoyomo.domain.application.domain.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.application.mapper.AnswerMapper;
import com.yoyomo.domain.application.domain.entity.Answer;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.mongo.AnswerRepository;
import com.yoyomo.domain.application.domain.vo.ApplicationReply;
import com.yoyomo.domain.application.exception.ApplicationReplySizeMismatchException;
import com.yoyomo.domain.item.domain.entity.Item;
import com.yoyomo.domain.item.domain.service.factory.ItemFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerSaveService {

	private final AnswerMapper answerMapper;
	private final AnswerRepository answerRepository;
	private final ItemFactory itemFactory;

	public Answer save(List<Item> items, UUID applicationId) {
		Answer answer = answerMapper.from(items, applicationId);
		return answerRepository.save(answer);
	}

	public void save(List<ApplicationReply> applicationReplies, List<Application> applications) {
		if (applicationReplies.size() != applications.size()) {
			throw new ApplicationReplySizeMismatchException();
		}

		List<Answer> answers = IntStream.range(0, applications.size())
			.mapToObj(order -> createAnswer(applicationReplies, applications, order))
			.toList();
		answerRepository.saveAll(answers);
	}

	private Answer createAnswer(List<ApplicationReply> applicationReplies, List<Application> applications, int order) {
		UUID applicationId = applications.get(order).getId();
		List<Item> items = itemFactory.createItem(applicationReplies.get(order));
		return Answer.builder()
			.applicationId(applicationId.toString())
			.items(items)
			.build();
	}
}
