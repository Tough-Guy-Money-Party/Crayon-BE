package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService {
    private final ManagerRepository managerRepository;

    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }
}
