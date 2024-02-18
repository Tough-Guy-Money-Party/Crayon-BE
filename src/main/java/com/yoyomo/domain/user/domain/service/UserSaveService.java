package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.ApplicantRepository;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService {
    private final ManagerRepository managerRepository;
    private final ApplicantRepository applicantRepository;

    public User save(User user) {
        if (user instanceof Applicant) {
            return applicantRepository.save((Applicant) user);
        } else if (user instanceof Manager) {
            return managerRepository.save((Manager) user);
        }
        return null;
    }
}
