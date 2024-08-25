package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yoyomo.domain.application.application.dto.request.ApplicationRequestDTO.Save;

@Service
@RequiredArgsConstructor
public class ApplicationSaveService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public Application save(Save dto, Process process){
        return applicationRepository.save(applicationMapper.from(dto, process));
    }
}
