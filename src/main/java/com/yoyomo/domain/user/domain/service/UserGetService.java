package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ApplicantRepository;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserGetService {
    private final ManagerRepository managerRepository;
    private final ApplicantRepository applicantRepository;

    public Manager findByEmail(String email) {
        return managerRepository.findByEmail(email).get();
    }

    public Boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }

    public Optional<Applicant> find(String name, String phone) {
        return applicantRepository.findByNameAndPhone(name, phone);
    }
}
