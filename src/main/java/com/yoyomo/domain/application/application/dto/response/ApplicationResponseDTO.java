package com.yoyomo.domain.application.application.dto.response;

import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.user.domain.entity.User;

import java.util.List;

import static com.yoyomo.domain.application.application.dto.response.AnswerResponseDTO.*;

public class ApplicationResponseDTO {

    public record Detail(
        String id,
        User user,
        Status status,
        Rating averageRating,
        Response answer
        // Interview
        // List<Evaluation>
    ) {}

    public record MyResponse(
            String id,
            ClubResponseDTO.Response club,
            List<ProcessResponseDTO.Info> processes,
            Integer currentStage
    ) {}
}
