package com.yoyomo.domain.recruitment.domain.service;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.RecruitmentRepository;
import com.yoyomo.domain.recruitment.exception.ModifiedRecruitmentException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentUpdateService {

	private final RecruitmentRepository recruitmentRepository;

	public void update(Recruitment recruitment, String formId) {
		recruitment.activate(formId);
		try {
			recruitmentRepository.save(recruitment);
		} catch (OptimisticLockingFailureException e) {
			throw new ModifiedRecruitmentException();
		}
	}

	public void delete(Recruitment recruitment) {
		recruitment.close();
	}
}
