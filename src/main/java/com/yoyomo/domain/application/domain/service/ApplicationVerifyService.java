package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.repository.ApplicationRepository;
import com.yoyomo.domain.application.exception.AlreadyAppliedException;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationVerifyService {
    private final ApplicationRepository applicationRepository;

    public void checkDuplicate(UUID recruitmentId, User applicant) {
        Optional<Application> application = applicationRepository.findByRecruitmentIdAndUser(recruitmentId, applicant);

        if (application.isPresent()) {
            throw new AlreadyAppliedException();
        }
    }
}
