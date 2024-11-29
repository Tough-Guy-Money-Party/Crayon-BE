package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ApplicationRequestDTO {

    public record Save(
            @Valid List<ItemRequest> answers
    ) {
        public Application toApplication(Recruitment recruitment, User applicant) {
            return Application.builder()
                    .user(applicant)
                    .recruitmentId(recruitment.getId())
                    .process(recruitment.getDocumentProcess())
                    .build();
        }
    }

    public record Update(
            @Valid List<ItemRequest> answers
    ) {
    }

    public record Stage(
            @NotNull List<String> ids,
            @NotNull Integer from,
            @NotNull Integer to
    ) {
    }
}
