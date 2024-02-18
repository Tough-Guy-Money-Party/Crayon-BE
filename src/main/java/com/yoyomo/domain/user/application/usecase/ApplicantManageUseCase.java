package com.yoyomo.domain.user.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.user.application.mapper.UserMapper;
import com.yoyomo.domain.user.domain.entity.Applicant;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicantManageUseCase {

    private final UserSaveService userSaveService;
    private final UserMapper userMapper;

    public Applicant create(ApplicationRequest request) {
        Applicant applicant = userMapper.from(request);
        return userSaveService.save(applicant);
    }

    public Void update() {
        return null;
    }

    public Void delete() {
        return null;
    }
}
