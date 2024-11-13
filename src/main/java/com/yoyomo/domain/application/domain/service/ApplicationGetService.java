package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO;
import com.yoyomo.domain.user.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {

    private final ApplicationRepository applicationRepository;
    private final UserMapper userMapper;

    public List<Application> findAll(UserRequestDTO.Find dto) {
        return applicationRepository.findAllByUserAndDeletedAtIsNull(userMapper.from(dto));
    }

    public List<Application> findAll(Long processId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return applicationRepository.findByProcessIdAndDeletedAtIsNull(processId, pageable);
    }

    public Page<Application> findAll(Process process, Pageable pageable) {
        return applicationRepository.findAllByProcessAndDeletedAtIsNull(process, pageable);
    }

    public Application find(String id) {
        return applicationRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public Page<Application> findByName(Recruitment recruitment, String name, Pageable pageable) {
        return applicationRepository.findAllByUser_NameAndProcess_RecruitmentAndDeletedAtIsNull(name, recruitment, pageable);
    }
}
