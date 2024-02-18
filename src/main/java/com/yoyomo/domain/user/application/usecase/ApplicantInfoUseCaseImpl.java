package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantInfoUseCaseImpl implements UserInfoUseCase{
    private final UserGetService userGetService;
    public Applicant get(String name, String phone) {
        return userGetService.find(name, phone).orElseThrow(UserNotFoundException::new);
    }
    @Override
    public Applicant get(Authentication authentication) {
        return null;
    }
}
