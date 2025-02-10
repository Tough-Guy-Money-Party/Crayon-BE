package com.yoyomo.domain.recruitment.domain.service;

import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentUpdateService {

    public void update(Recruitment recruitment, String formId) {
        recruitment.activate(formId);
    }

    public void delete(Recruitment recruitment) {
        recruitment.close();
    }
}
