package com.yoyomo.domain.application.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.Interview;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationUpdateService {

    public void delete(Application application) {
        application.delete();
    }

    public void update(Application application, Interview interview) {
        application.addInterview(interview);
    }
}
