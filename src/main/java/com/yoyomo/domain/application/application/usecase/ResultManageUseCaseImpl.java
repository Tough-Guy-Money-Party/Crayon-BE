package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultManageUseCaseImpl implements ResultManageUseCase{

    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<Result> announce(Find dto) {
        return applicationGetService.findAll(dto).stream()
                .map(applicationMapper::toResult)
                .toList();
    }
}
