package com.yoyomo.domain.application.application.dto.request;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.item.application.dto.req.ItemRequest;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ApplicationSaveRequest(
        @NotBlank
        String userName,

        @Email
        @NotBlank
        String email,

        @Size(max = 13)
        @NotBlank
        String tel,

        @Valid
        List<ItemRequest> answers
) {
    public Application toApplication(Recruitment recruitment, User applicant) {
        return Application.builder()
                .user(applicant)
                .recruitmentId(recruitment.getId())
                .process(recruitment.getDocumentProcess())
                .userName(userName)
                .email(email)
                .tel(tel)
                .build();
    }
}
