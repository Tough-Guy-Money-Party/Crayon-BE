package com.yoyomo.domain.recruitment.domain.service;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yoyomo.domain.recruitment.application.mapper.ProcessMapper;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessSaveService {

	private final ProcessRepository processRepository;
	private final ProcessMapper processMapper;

	public List<Process> saveAll(List<Process> processes, Recruitment recruitment) {
		processes.forEach(process -> process.addRecruitment(recruitment));
		return processRepository.saveAll(processes);
	}

	public List<Process> saveAll(List<Process> processes) {
		return processRepository.saveAll(processes);
	}

	public Process save(Update dto, Recruitment recruitment) {
		return processRepository.save(processMapper.from(dto, recruitment));
	}
}
