package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.res.ProcessResultsResponse;

import java.util.List;

public interface ResultConfirmUseCase {

    List<ProcessResultsResponse> read(String clubId);
}
