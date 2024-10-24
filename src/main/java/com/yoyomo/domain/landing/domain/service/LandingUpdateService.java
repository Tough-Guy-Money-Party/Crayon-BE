package com.yoyomo.domain.landing.domain.service;

import com.yoyomo.domain.landing.application.dto.request.LandingRequestDTO.Style;
import com.yoyomo.domain.landing.domain.entity.Landing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandingUpdateService {
    public void update(Landing landing, Style dto) {
        landing.updateStyle(dto);
    }
}
