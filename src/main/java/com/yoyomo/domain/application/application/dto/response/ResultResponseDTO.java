package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.enums.Status;

public class ResultResponseDTO {

    public record Result(
        Status result
    ) {}
}
