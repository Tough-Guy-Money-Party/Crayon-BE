package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.application.application.mapper.ApplicationMapper;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;
import com.yoyomo.domain.user.application.mapper.UserMapperImpl;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultManageUseCaseImpl implements ResultManageUseCase {

    private final ApplicationGetService applicationGetService;
    private final ApplicationMapper applicationMapper;
    private final UserMapperImpl userMapper;

    @Override
    public List<Result> announce(Find dto) {
        User user = userMapper.from(dto);

        return applicationGetService.findAll(user).stream()
                .map(applicationMapper::toResult)
                .toList();
    }
}
