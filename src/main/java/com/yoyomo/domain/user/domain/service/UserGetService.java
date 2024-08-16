package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserGetService {
    private final ManagerRepository managerRepository;

    public Manager findByEmail(String email) {
        return managerRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public Boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }

    public Manager find(Long id) {
        return managerRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
