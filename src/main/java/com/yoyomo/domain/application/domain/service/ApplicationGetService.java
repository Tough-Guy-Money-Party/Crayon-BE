package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.ApplicationNotFoundException;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
    private final ApplicationRepository applicationRepository;

    public Application find(String applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);
    }

    public List<Application> findAll(String recruitmentId) {
        return applicationRepository.findAllByRecruitmentId(recruitmentId);
    }

    public List<Application> findAll(User user) {
        return applicationRepository.findAllByUser(user);
    }

    public boolean find(User user, String recruitmentId) {
        return applicationRepository.existsByUserAndRecruitment_Id(user, recruitmentId);
    }
}
