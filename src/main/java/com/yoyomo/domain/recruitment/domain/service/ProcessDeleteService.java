package com.yoyomo.domain.recruitment.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessDeleteService {

	private final ProcessRepository processRepository;

	public void deleteAll(List<Process> processes) {
		processRepository.deleteAllInBatch(processes);
	}

	public void deleteAllByRecruitment(Recruitment recruitment) {
		processRepository.deleteAllByRecruitment(recruitment);
	}
}
