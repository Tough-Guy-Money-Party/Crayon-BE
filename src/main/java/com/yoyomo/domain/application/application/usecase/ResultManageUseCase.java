package com.yoyomo.domain.application.application.usecase;

import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO;
import com.yoyomo.domain.application.application.dto.response.ResultResponseDTO.Result;
import com.yoyomo.domain.application.domain.service.ApplicationGetService;
import com.yoyomo.domain.user.application.dto.request.UserRequestDTO.Find;
import com.yoyomo.domain.user.application.mapper.UserMapper;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultManageUseCase {

    private final ApplicationGetService applicationGetService;
    private final UserMapper userMapper;

    public List<Result> announce(Find dto) {
        User user = userMapper.from(dto);

        return applicationGetService.findAll(user).stream()
                .map(ResultResponseDTO.Result::toResponse)
                .toList();
    }
}
