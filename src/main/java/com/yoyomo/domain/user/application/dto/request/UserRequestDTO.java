package com.yoyomo.domain.user.application.dto.request;

public class UserRequestDTO {

    public record Announce(
            String name,
            String email,
            String tel
    ) {}
}
