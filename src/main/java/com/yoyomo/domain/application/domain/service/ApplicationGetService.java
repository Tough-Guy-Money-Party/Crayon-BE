package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO;
import com.yoyomo.domain.user.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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

    public Application find(String id) {
        return applicationRepository.findByIdAndDeletedAtIsNull(UUID.fromString(id))
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> findByName(Recruitment recruitment, String name) {
        return applicationRepository.findAllByUser_NameAndDeletedAtIsNull(name).stream()
                .filter(application -> application.containsInRecruitment(recruitment))
                .toList();
    }
}
