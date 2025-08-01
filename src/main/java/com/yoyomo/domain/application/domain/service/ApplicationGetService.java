package com.yoyomo.domain.application.domain.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yoyomo.domain.application.application.usecase.dto.ApplicationCondition;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import com.yoyomo.domain.application.domain.repository.dto.ProcessApplicant;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {

	private final ApplicationRepository applicationRepository;

	public List<Application> findAll(User user) {
		return applicationRepository.findAllByUserAndDeletedAtIsNull(user);
	}

	public List<Application> findAll(Long processId, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return applicationRepository.findByProcessIdAndDeletedAtIsNull(processId, pageable);
	}

	public Page<ApplicationWithStatus> findAll(
		Process process, ApplicationCondition condition, Pageable pageable
	) {
		return applicationRepository.findAllWithStatusByProcess(process, condition, pageable);
	}

	public List<Application> findAllOrderByName(Process process) {
		return applicationRepository.findAllByProcessOrderByUserName(process);
	}

	public List<ApplicationWithStatus> findAllWithProcessResult(Process process) {
		return applicationRepository.findAllWithStatusByProcess(process);
	}

	public Application find(String id) {
		return applicationRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public Application find(UUID applicationId) {
		return applicationRepository.findByIdAndDeletedAtIsNull(applicationId)
			.orElseThrow(ApplicationNotFoundException::new);
	}

	public Page<Application> findByName(String name, Process process, Pageable pageable) {
		return applicationRepository.findAllByUserNameContainingAndProcessAndDeletedAtIsNull(name, process, pageable);
	}

	public Map<Process, Long> countInProcesses(UUID recruitmentId, List<Process> processes) {
		return applicationRepository.countByRecruitmentAndProcess(recruitmentId, processes)
			.stream()
			.collect(Collectors.toMap(ProcessApplicant::process, ProcessApplicant::applicantCount));
	}
}
