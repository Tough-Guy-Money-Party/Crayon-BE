package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResponse;

import java.util.List;

public interface ProcessUseCase {

    List<ProcessResponse> readAll(String recruitmentId);
}
