package com.yoyomo.domain.recruitment.application.usecase;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequest.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.club.domain.service.ClubManagerAuthService;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponse;
import com.yoyomo.domain.recruitment.domain.dto.ProcessWithApplicantCount;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.ProcessStep;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.recruitment.domain.service.ProcessDeleteService;
import com.yoyomo.domain.recruitment.domain.service.ProcessGetService;
import com.yoyomo.domain.recruitment.domain.service.ProcessSaveService;
import com.yoyomo.domain.recruitment.domain.service.RecruitmentGetService;
import com.yoyomo.domain.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessManageUseCase {

	private final ProcessSaveService processSaveService;
	private final ProcessDeleteService processDeleteService;
	private final RecruitmentGetService recruitmentGetService;
	private final ClubManagerAuthService clubManagerAuthService;
	private final ProcessGetService processGetService;

	public List<Process> save(List<Process> processes) {
		return processSaveService.saveAll(processes);
	}

	@Transactional(readOnly = true)
	public List<ProcessResponse> readAll(UUID recruitmentId, User user) {
		clubManagerAuthService.checkAuthorization(recruitmentId, user);
		List<ProcessWithApplicantCount> processWithApplicantCount = processGetService.findAllWithApplicantCount(
			recruitmentId);

		return ProcessResponse.toResponse(processWithApplicantCount);
	}

	@Transactional
	public List<Process> update(List<Update> dto, Recruitment recruitment) {
		processDeleteService.deleteAll(recruitment.getProcesses());
		recruitment.clearProcesses();

		return dto.stream()
			.map(update -> processSaveService.save(update, recruitment))
			.sorted(Comparator.comparing(Process::getStage))
			.toList();
	}

	@Transactional
	public void updateStep(UUID recruitmentId, Long processId, ProcessStep step, User user) {
		clubManagerAuthService.checkAuthorization(recruitmentId, user);

		Recruitment recruitment = recruitmentGetService.find(recruitmentId);
		Process process = processGetService.find(processId);

		Type currentProcess = recruitment.getCurrentProcess();

		process.checkMovable(currentProcess, step);

		process.updateStep(step);
	}
}

