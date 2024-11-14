package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProcessUpdateService {

    public void update(Process to, Application application) {
//        from.removeApplication(application);
//        to.addApplication(application);
        application.update(to);
    }
}
