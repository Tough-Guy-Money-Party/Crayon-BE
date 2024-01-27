package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.req.ApplicationRequest;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.service.ApplicationSaveService;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyUseCaseImpl implements ApplyUseCase {
    private final ApplicationSaveService applicationSaveService;
    private final ApplicationMapper applicationMapper;

    public void create(User user, ApplicationRequest applicationRequest) {
        Application application = applicationMapper.from(user.getId(), applicationRequest);
        applicationSaveService.save(application);
    }

    public void read(User user) {


    }
}
