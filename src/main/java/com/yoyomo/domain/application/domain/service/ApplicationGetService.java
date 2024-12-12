package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.domain.repository.dto.ProcessApplicant;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.repository.ProcessRepository;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {

    private final ApplicationRepository applicationRepository;
    private final ProcessRepository processRepository;

    public List<Application> findAll(User user) {
        return applicationRepository.findAllByUserAndDeletedAtIsNull(user);
    }

    public List<Application> findAll(Long processId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return applicationRepository.findByProcessIdAndDeletedAtIsNull(processId, pageable);
    }

    public Page<Application> findAll(Process process, Pageable pageable) {
        return applicationRepository.findAllByProcessOrderByPending(process, pageable);
    }

    public List<Application> findAll(Process process, Status status) {
        return applicationRepository.findAllByProcessAndStatusAndDeletedAtIsNull(process, status);
    }

    public List<Application> findAll(Process process) {
        return applicationRepository.findAllByProcessOrderByPending(process);
    }

    public Application find(String id) {
        return applicationRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<UUID> getApplicationIds(Page<Application> applications) {
        return applications.stream()
                .map(Application::getId)
                .toList();
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
